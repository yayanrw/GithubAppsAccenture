package com.heyproject.githubapps.di

import android.content.Context
import androidx.room.Room
import com.heyproject.githubapps.data.datasource.local.dao.RemoteKeysDao
import com.heyproject.githubapps.data.datasource.local.dao.UserDao
import com.heyproject.githubapps.data.datasource.local.dao.UserDetailDao
import com.heyproject.githubapps.data.datasource.local.database.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 06:28
Github : https://github.com/yayanrw
 **/

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("yayan_accenture".toCharArray())
    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase =
        Room.databaseBuilder(
            context, GithubDatabase::class.java, "GithubApps.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()

    @Provides
    fun provideUserDao(database: GithubDatabase): UserDao = database.userDao()

    @Provides
    fun provideUserDetailDao(database: GithubDatabase): UserDetailDao = database.userDetailDao()

    @Provides
    fun provideRemoteKeysDao(database: GithubDatabase): RemoteKeysDao = database.remoteKeysDao()
}