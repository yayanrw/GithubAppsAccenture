package com.heyproject.githubapps.di

import android.content.Context
import com.heyproject.githubapps.domain.repository.GithubRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 07:14
Github : https://github.com/yayanrw
 **/

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository(): GithubRepository
}