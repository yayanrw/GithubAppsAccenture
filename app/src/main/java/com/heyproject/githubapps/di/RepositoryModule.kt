package com.heyproject.githubapps.di

import com.heyproject.githubapps.data.repository.GithubRepositoryImpl
import com.heyproject.githubapps.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 07:18
Github : https://github.com/yayanrw
 **/

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository
}