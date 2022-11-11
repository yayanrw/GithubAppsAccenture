package com.heyproject.githubapps.data.datasource.local

import com.heyproject.githubapps.data.datasource.local.entity.UserDetailEntity
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import com.heyproject.githubapps.data.utils.DataResource
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 08:23
Github : https://github.com/yayanrw
 **/

interface LocalDataSource {
    suspend fun insertUsers(users: List<UserEntity>)
    suspend fun deleteUsers()
    suspend fun searchUsers(query: String): Flow<DataResource<List<UserEntity>>>
    suspend fun insertUserDetail(user: UserDetailEntity)
    suspend fun getUserDetail(login: String): Flow<DataResource<UserDetailEntity>>
    suspend fun deleteUserDetail()
}