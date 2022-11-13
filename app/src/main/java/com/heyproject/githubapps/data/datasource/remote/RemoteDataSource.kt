package com.heyproject.githubapps.data.datasource.remote

import com.heyproject.githubapps.BuildConfig
import com.heyproject.githubapps.common.FollowType
import com.heyproject.githubapps.data.datasource.remote.api.GithubService
import com.heyproject.githubapps.data.datasource.remote.dto.UserDetailDto
import com.heyproject.githubapps.data.datasource.remote.dto.UserDto
import com.heyproject.githubapps.data.datasource.remote.response.UserSearchResponse
import com.heyproject.githubapps.data.utils.DataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
Written by Yayan Rahmat Wijaya on 11/10/2022 23:31
Github : https://github.com/yayanrw
 **/

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val githubService: GithubService) :
    RemoteDataSource {
    override suspend fun fetchUserDetail(login: String): Flow<DataResource<UserDetailDto>> {
        return flow {
            try {
                val response = githubService.getUserDetail(
                    BuildConfig.API_KEY, login,
                )

                emit(DataResource.Success(response))
            } catch (e: Exception) {
                emit(DataResource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchUsers(
        page: Int, perPage: Int
    ): Flow<DataResource<UserSearchResponse>> {
        return flow {
            try {
                val response = githubService.getUsers(
                    BuildConfig.API_KEY,
                    page = page,
                    perPage = perPage,
                )
                if (response.totalCount > 0) {
                    emit(DataResource.Success(response))
                } else {
                    emit(DataResource.Empty)
                }
            } catch (e: Exception) {
                emit(DataResource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchSearchUser(query: String): Flow<DataResource<List<UserDto>>> {
        return flow {
            try {
                val response = githubService.getSearchUsers(
                    BuildConfig.API_KEY, query
                )
                if (response.totalCount > 0) {
                    emit(DataResource.Success(response.items))
                } else {
                    emit(DataResource.Empty)
                }
            } catch (e: Exception) {
                emit(DataResource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchUserFollow(
        login: String, followType: FollowType
    ): Flow<DataResource<List<UserDto>>> {
        return flow {
            try {
                val response = githubService.getUserFollow(
                    BuildConfig.API_KEY, login, followType
                )
                if (response.isNotEmpty()) {
                    emit(DataResource.Success(response))
                } else {
                    emit(DataResource.Empty)
                }
            } catch (e: Exception) {
                emit(DataResource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}

interface RemoteDataSource {
    suspend fun fetchUserDetail(login: String): Flow<DataResource<UserDetailDto>>
    suspend fun fetchUsers(
        page: Int, perPage: Int,
    ): Flow<DataResource<UserSearchResponse>>

    suspend fun fetchSearchUser(query: String): Flow<DataResource<List<UserDto>>>
    suspend fun fetchUserFollow(
        login: String, followType: FollowType,
    ): Flow<DataResource<List<UserDto>>>
}