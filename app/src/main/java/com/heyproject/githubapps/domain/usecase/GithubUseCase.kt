package com.heyproject.githubapps.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/12/2022 16:39
Github : https://github.com/yayanrw
 **/

interface GithubUseCase {
    fun getUsers(): LiveData<PagingData<User>>
    fun getUser(login: String): Flow<User>
    fun getFavoriteUsers(): Flow<List<UserDetail>>
    fun getUserDetail(login: String): Flow<ViewResource<UserDetail?>>
    fun getUserDetailFlat(login: String): Flow<UserDetail>
    fun updateUser(user: User, state: Boolean)
    fun setUserFavorite(userDetail: UserDetail, newState: Boolean)
    suspend fun insertUserDetail(userDetail: UserDetail)
    suspend fun deleteUsers()
    suspend fun deleteUserDetail()
    fun searchUsers(query: String): Flow<ViewResource<List<User>>>
    fun getFollowers(login: String): Flow<ViewResource<List<User>>>
    fun getFollowings(login: String): Flow<ViewResource<List<User>>>
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}