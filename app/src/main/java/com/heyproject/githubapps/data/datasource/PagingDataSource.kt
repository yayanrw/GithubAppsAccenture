package com.heyproject.githubapps.data.datasource

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.heyproject.githubapps.BuildConfig
import com.heyproject.githubapps.data.datasource.local.database.GithubDatabase
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import com.heyproject.githubapps.data.datasource.remote.api.GithubService
import javax.inject.Inject
import javax.inject.Singleton

/**
Written by Yayan Rahmat Wijaya on 11/12/2022 17:27
Github : https://github.com/yayanrw
 **/

@Singleton
class PagingDataSource @Inject constructor(
    private val githubDatabase: GithubDatabase,
    private val githubService: GithubService,
) {
    fun getUsers(): LiveData<PagingData<UserEntity>> {
        @OptIn(ExperimentalPagingApi::class) return Pager(config = PagingConfig(pageSize = 10),
            remoteMediator = UsersRemoteMediator(
                githubDatabase, githubService, BuildConfig.API_KEY
            ),
            pagingSourceFactory = {
                githubDatabase.userDao().getUsers()
            }).liveData
    }
}