package com.heyproject.githubapps.data.datasource.local

import com.heyproject.githubapps.data.datasource.local.dao.UserDao
import com.heyproject.githubapps.data.datasource.local.dao.UserDetailDao
import com.heyproject.githubapps.data.datasource.local.datastore.SettingDataStore
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
class LocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val userDetailDao: UserDetailDao,
    private val settingDataStore: SettingDataStore,
) : LocalDataSource {
    override suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)

    override suspend fun deleteUsers() = userDao.deleteUsers()
    override fun getUser(login: String): Flow<UserEntity> = userDao.getUser(login)

    override fun searchUsers(query: String): Flow<List<UserEntity>> = userDao.searchUser(query)

    override suspend fun insertUserDetail(user: UserDetailEntity) =
        userDetailDao.insertUserDetail(user)

    override fun getUserDetail(login: String): Flow<UserDetailEntity> =
        userDetailDao.getUserDetail(login)

    override suspend fun deleteUserDetail() = userDetailDao.deleteUserDetail()

    override fun getFavoriteUsers(): Flow<List<UserDetailEntity>> = userDetailDao.getFavoriteUsers()

    override fun updateUser(user: UserEntity) = userDao.updateUser(user)
    override fun setUserFavorite(userDetailEntity: UserDetailEntity) =
        userDetailDao.updateFavoriteUser(userDetailEntity)

    override fun getThemeSetting(): Flow<Boolean> = settingDataStore.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        settingDataStore.saveThemeSetting(isDarkModeActive)
}

interface LocalDataSource {
    suspend fun insertUser(user: UserEntity)
    suspend fun deleteUsers()
    fun getUser(login: String): Flow<UserEntity>
    fun searchUsers(query: String): Flow<List<UserEntity>>
    suspend fun insertUserDetail(user: UserDetailEntity)
    fun getUserDetail(login: String): Flow<UserDetailEntity>
    suspend fun deleteUserDetail()
    fun getFavoriteUsers(): Flow<List<UserDetailEntity>>
    fun updateUser(user: UserEntity)
    fun setUserFavorite(userDetailEntity: UserDetailEntity)
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}