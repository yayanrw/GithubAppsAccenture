package com.heyproject.githubapps.di

import com.heyproject.githubapps.data.repository.GithubRepositoryImpl
import com.heyproject.githubapps.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 07:18
Github : https://github.com/yayanrw
 **/

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository
}