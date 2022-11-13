package com.heyproject.githubapps.data.datasource.local

import com.heyproject.githubapps.data.datasource.local.dao.UserDao
import com.heyproject.githubapps.data.datasource.local.dao.UserDetailDao
import com.heyproject.githubapps.data.datasource.local.entity.UserDetailEntity
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 08:23
Github : https://github.com/yayanrw
 **/

@Singleton
class LocalDataSource @Inject constructor(
    private val userDao: UserDao, private val userDetailDao: UserDetailDao,
) {
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)

    suspend fun deleteUsers() = userDao.deleteUsers()

    fun searchUsers(query: String): Flow<List<UserEntity>> = userDao.searchUser(query)

    suspend fun insertUserDetail(user: UserDetailEntity) = userDetailDao.insertUserDetail(user)

    fun getUserDetail(login: String): Flow<UserDetailEntity> = userDetailDao.getUserDetail(login)

    suspend fun deleteUserDetail() = userDetailDao.deleteUserDetail()

    fun getFavoriteUsers(): Flow<List<UserEntity>> = userDao.getFavoriteUsers()

    fun updateUser(user: UserEntity) = userDao.updateUser(user)
}