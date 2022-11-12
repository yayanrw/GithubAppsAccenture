package com.heyproject.githubapps.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingData
import androidx.paging.map
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.data.datasource.PagingDataSource
import com.heyproject.githubapps.data.datasource.local.LocalDataSource
import com.heyproject.githubapps.data.datasource.remote.RemoteDataSource
import com.heyproject.githubapps.data.datasource.remote.dto.UserDetailDto
import com.heyproject.githubapps.data.datasource.remote.dto.UserDto
import com.heyproject.githubapps.data.utils.AppExecutors
import com.heyproject.githubapps.data.utils.DataResource
import com.heyproject.githubapps.data.utils.NetworkBoundResource
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.model.toDomain
import com.heyproject.githubapps.domain.model.toEntity
import com.heyproject.githubapps.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
Written by Yayan Rahmat Wijaya on 11/12/2022 17:20
Github : https://github.com/yayanrw
 **/

class GithubRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val pagingDataSource: PagingDataSource,
    private val appExecutors: AppExecutors,
) : GithubRepository {
    override fun getUsers(): LiveData<PagingData<User>> {
        return pagingDataSource.getUsers().map { pagingData ->
            pagingData.map { userEntity ->
                userEntity.toDomain()
            }
        }
    }

    override fun getFavoriteUsers(): Flow<List<User>> {
        return localDataSource.getFavoriteUsers().map { list ->
            list.map { userEntity ->
                userEntity.toDomain()
            }
        }
    }

    override fun getUserDetail(login: String): Flow<ViewResource<UserDetail>> =
        object : NetworkBoundResource<UserDetail, UserDetailDto>() {
            override fun loadFromDB(): Flow<UserDetail> {
                return localDataSource.getUserDetail(login).map { userDetailEntity ->
                    userDetailEntity.toDomain()
                }
            }

            override fun shouldFetch(data: UserDetail?): Boolean = true

            override suspend fun createCall(): Flow<DataResource<UserDetailDto>> {
                return remoteDataSource.fetchUserDetail(login)
            }

            override suspend fun saveCallResult(data: UserDetailDto) {
                return localDataSource.insertUserDetail(data.toEntity())
            }

        }.asFlow()

    override fun updateUser(user: User, state: Boolean) {
        appExecutors.diskIO().execute {
            val newUser = User(
                id = user.id,
                login = user.login,
                avatarUrl = user.avatarUrl,
                type = user.type,
                isFavorite = state,
            )
            localDataSource.updateUser(newUser.toEntity())
        }
    }

    override suspend fun insertUserDetail(userDetail: UserDetail) {
        localDataSource.insertUserDetail(userDetail.toEntity())
    }

    override suspend fun deleteUsers() {
        localDataSource.deleteUsers()
    }

    override suspend fun deleteUserDetail() {
        localDataSource.deleteUserDetail()
    }

    override fun searchUsers(query: String): Flow<ViewResource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserDto>>() {
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.searchUsers(query).map { list ->
                    list.map { userEntity ->
                        userEntity.toDomain()
                    }
                }
            }

            override fun shouldFetch(data: List<User>?): Boolean = true

            override suspend fun createCall(): Flow<DataResource<List<UserDto>>> {
                return remoteDataSource.fetchSearchUser(query)
            }

            override suspend fun saveCallResult(data: List<UserDto>) {
                data.forEach { userDto ->
                    localDataSource.insertUser(userDto.toEntity())
                }
            }

        }.asFlow()

    override suspend fun getFollowers(login: String): Flow<ViewResource<List<User>>> {
        return flow {
            emit(ViewResource.Loading())

            when (val response = remoteDataSource.fetchSearchUser(login).first()) {
                is DataResource.Success -> {
                    emit(ViewResource.Success(response.data.map {
                        it.toDomain()
                    }))
                }
                is DataResource.Empty -> {
                    emit(ViewResource.Success(null))
                }
                is DataResource.Error -> {
                    emit(ViewResource.Error(response.errorMessage))
                }
            }
        }
    }

    override suspend fun getFollowings(login: String): Flow<ViewResource<List<User>>> {
        TODO("Not yet implemented")
    }
}