package com.heyproject.githubapps.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.heyproject.githubapps.data.datasource.local.database.GithubDatabase
import com.heyproject.githubapps.data.datasource.local.entity.RemoteKeysEntity
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import com.heyproject.githubapps.data.datasource.remote.api.GithubService
import com.heyproject.githubapps.domain.model.toEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 13:05
Github : https://github.com/yayanrw
 **/

@Singleton
@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator @Inject constructor(
    private val githubDatabase: GithubDatabase,
    private val githubService: GithubService,
    private val token: String,
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, UserEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }

        try {
            val response =
                githubService.getUsers(token, page = page, perPage = state.config.pageSize)
            val endOfPaginationReached = response.items.isEmpty()

            githubDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    githubDatabase.remoteKeysDao().deleteRemoteKeys()
                    githubDatabase.userDao().deleteUsers()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.items.map { userDto ->
                    RemoteKeysEntity(id = userDto.id, prevKey = prevKey, nextKey = nextKey)
                }

                githubDatabase.remoteKeysDao().insertAll(keys)

                response.items.forEach { userDto ->

                    val users = userDto.toEntity()
                    githubDatabase.userDao().insertUser(users)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, UserEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            githubDatabase.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            githubDatabase.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                githubDatabase.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}