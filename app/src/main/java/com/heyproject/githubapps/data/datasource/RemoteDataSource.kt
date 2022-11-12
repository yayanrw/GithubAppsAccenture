package com.heyproject.githubapps.data.datasource

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

/**
Written by Yayan Rahmat Wijaya on 11/10/2022 23:31
Github : https://github.com/yayanrw
 **/

class RemoteDataSourceImpl(private val githubService: GithubService) : RemoteDataSource {
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

    override suspend fun fetchUserDetail(userName: String): Flow<DataResource<UserDetailDto>> {
        return flow {
            try {
                val response = githubService.getUserDetail(
                    BuildConfig.API_KEY, userName,
                )

                if (response.id != null) {
                    emit(DataResource.Success(response))
                } else {
                    emit(DataResource.Empty)
                }
            } catch (e: Exception) {
                emit(DataResource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchSearchUser(query: String): Flow<DataResource<UserSearchResponse>> {
        return flow {
            try {
                val response = githubService.getSearchUsers(
                    BuildConfig.API_KEY, query
                )
                if (response.totalCount > 0) {
                    emit(DataResource.Success(response))
                } else {
                    emit(DataResource.Empty)
                }
            } catch (e: Exception) {
                emit(DataResource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchUserFollow(
        userName: String, followType: FollowType
    ): Flow<DataResource<List<UserDto>>> {
        return flow {
            try {
                val response = githubService.getUserFollow(
                    BuildConfig.API_KEY, userName, followType
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
    suspend fun fetchUsers(
        page: Int,
        perPage: Int,
    ): Flow<DataResource<UserSearchResponse>>

    suspend fun fetchUserDetail(
        userName: String,
    ): Flow<DataResource<UserDetailDto>>

    suspend fun fetchSearchUser(
        query: String,
    ): Flow<DataResource<UserSearchResponse>>

    suspend fun fetchUserFollow(
        userName: String,
        followType: FollowType,
    ): Flow<DataResource<List<UserDto>>>
}