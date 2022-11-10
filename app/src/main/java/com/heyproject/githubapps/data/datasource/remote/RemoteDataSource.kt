package com.heyproject.githubapps.data.datasource.remote

import com.heyproject.githubapps.common.FollowType
import com.heyproject.githubapps.data.datasource.remote.dto.UserDetailDto
import com.heyproject.githubapps.data.datasource.remote.dto.UserDto
import com.heyproject.githubapps.data.datasource.remote.response.UserSearchResponse
import com.heyproject.githubapps.data.utils.DataResource
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/10/2022 23:31
Github : https://github.com/yayanrw
 **/



interface RemoteDataSource {
    suspend fun fetchUsers(
        token: String,
        since: Int,
        perPage: Int,
    ): Flow<DataResource<List<UserDto>>>

    suspend fun fetchUserDetail(
        token: String,
        userName: String,
    ): Flow<DataResource<UserDetailDto>>

    suspend fun fetchSearchUser(
        token: String,
        query: String,
    ): Flow<DataResource<UserSearchResponse>>

    suspend fun fetchUserFollow(
        token: String,
        userName: String,
        followType: FollowType,
    ): Flow<DataResource<List<UserDto>>>
}