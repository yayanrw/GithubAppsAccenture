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

/**
Written by Yayan Rahmat Wijaya on 11/10/2022 23:31
Github : https://github.com/yayanrw
 **/

class RemoteDataSourceImpl(private val githubService: GithubService) : RemoteDataSource {
    override suspend fun fetchUsers(since: Int, perPage: Int): Flow<DataResource<List<UserDto>>> {
        return flow {
            try {
                val response = githubService.getUsers(
                    BuildConfig.API_KEY,
                    since,
                    perPage,
                )
                if (response.isNotEmpty()) {
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
                emit(DataResource.Success(response))
            } catch (e: Exception) {
                emit(DataResource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchSearchUser(query: String): Flow<DataResource<UserSearchResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserFollow(
        userName: String, followType: FollowType
    ): Flow<DataResource<List<UserDto>>> {
        TODO("Not yet implemented")
    }

}

interface RemoteDataSource {
    suspend fun fetchUsers(
        since: Int,
        perPage: Int,
    ): Flow<DataResource<List<UserDto>>>

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