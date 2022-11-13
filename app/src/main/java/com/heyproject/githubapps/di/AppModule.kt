package com.heyproject.githubapps.di

import com.heyproject.githubapps.domain.usecase.GithubInterActor
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 19:55
Github : https://github.com/yayanrw
 **/

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGithubUseCase(githubInterActor: GithubInterActor): GithubUseCase

}