package com.heyproject.githubapps.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/12/2022 16:57
Github : https://github.com/yayanrw
 **/

interface GithubRepository {
    fun getUsers(): LiveData<PagingData<User>>
    fun getFavoriteUsers(): Flow<List<User>>
    fun getUserDetail(login: String): Flow<UserDetail>
    fun updateUser(user: User, state: Boolean)
    fun insertUser(user: User)
    fun insertUserDetail(userDetail: UserDetail)
    fun deleteUsers()
    fun deleteUserDetail()
    fun searchUsers(query: String): Flow<List<User>>
    fun getFollowers(login: String): Flow<List<User>>
    fun getFollowings(login: String): Flow<List<User>>
}