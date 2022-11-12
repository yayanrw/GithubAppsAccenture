package com.heyproject.githubapps.data.datasource.local

import com.heyproject.githubapps.data.datasource.local.dao.UserDao
import com.heyproject.githubapps.data.datasource.local.dao.UserDetailDao
import com.heyproject.githubapps.data.datasource.local.entity.UserDetailEntity
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 08:23
Github : https://github.com/yayanrw
 **/

class LocalDataSourceImpl(private val userDao: UserDao, private val userDetailDao: UserDetailDao) :
    LocalDataSource {
    override suspend fun insertUsers(user: UserEntity) = userDao.insertUsers(user)

    override suspend fun deleteUsers() = userDao.deleteUsers()

    override suspend fun searchUsers(query: String): Flow<List<UserEntity>> =
        userDao.searchUser(query)

    override suspend fun insertUserDetail(user: UserDetailEntity) =
        userDetailDao.insertUserDetail(user)

    override suspend fun getUserDetail(login: String): Flow<UserDetailEntity> =
        userDetailDao.getUserDetail(login)

    override suspend fun deleteUserDetail() = userDetailDao.deleteUserDetail()
}

interface LocalDataSource {
    suspend fun insertUsers(user: UserEntity)
    suspend fun deleteUsers()
    suspend fun searchUsers(query: String): Flow<List<UserEntity>>
    suspend fun insertUserDetail(user: UserDetailEntity)
    suspend fun getUserDetail(login: String): Flow<UserDetailEntity>
    suspend fun deleteUserDetail()
}