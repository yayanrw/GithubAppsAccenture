package com.heyproject.githubapps.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.heyproject.githubapps.data.datasource.local.dao.RemoteKeysDao
import com.heyproject.githubapps.data.datasource.local.dao.UserDao
import com.heyproject.githubapps.data.datasource.local.dao.UserDetailDao
import com.heyproject.githubapps.data.datasource.local.entity.RemoteKeysEntity
import com.heyproject.githubapps.data.datasource.local.entity.UserDetailEntity
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 08:19
Github : https://github.com/yayanrw
 **/

@Database(
    entities = [
        UserEntity::class,
        UserDetailEntity::class,
        RemoteKeysEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userDetailDao(): UserDetailDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}