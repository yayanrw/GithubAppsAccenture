package com.heyproject.githubapps.di

import com.heyproject.githubapps.data.datasource.PagingDataSource
import com.heyproject.githubapps.data.datasource.PagingDataSourceImpl
import com.heyproject.githubapps.data.datasource.local.LocalDataSource
import com.heyproject.githubapps.data.datasource.local.LocalDataSourceImpl
import com.heyproject.githubapps.data.datasource.remote.RemoteDataSource
import com.heyproject.githubapps.data.datasource.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 22:27
Github : https://github.com/yayanrw
 **/

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun providePagingDataSource(pagingDataSourceImpl: PagingDataSourceImpl): PagingDataSource
}