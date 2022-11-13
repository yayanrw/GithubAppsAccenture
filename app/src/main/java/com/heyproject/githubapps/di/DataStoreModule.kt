package com.heyproject.githubapps.di

import android.content.Context
import com.heyproject.githubapps.data.datasource.local.datastore.SettingDataStore
import com.heyproject.githubapps.data.datasource.local.datastore.SettingDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 23:47
Github : https://github.com/yayanrw
 **/

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Singleton
    @Provides
    fun providesSettingDataStore(
        @ApplicationContext context: Context
    ): SettingDataStore = SettingDataStoreImpl(context)
}