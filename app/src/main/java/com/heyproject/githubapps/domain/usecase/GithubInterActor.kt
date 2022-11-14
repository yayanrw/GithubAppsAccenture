package com.heyproject.githubapps.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 06:08
Github : https://github.com/yayanrw
 **/

class GithubInterActor @Inject constructor(private val githubRepository: GithubRepository) :
    GithubUseCase {
    override fun getUsers(): LiveData<PagingData<User>> = githubRepository.getUsers()
    override fun getUser(login: String): Flow<User> = githubRepository.getUser(login)

    override fun getFavoriteUsers(): Flow<List<UserDetail>> = githubRepository.getFavoriteUsers()

    override fun getUserDetail(login: String): Flow<ViewResource<UserDetail>> =
        githubRepository.getUserDetail(login)

    override fun updateUser(user: User, state: Boolean) = githubRepository.updateUser(user, state)
    override fun updateUserDetail(userDetail: UserDetail, newState: Boolean) =
        githubRepository.updateUserDetail(userDetail, newState)

    override suspend fun insertUserDetail(userDetail: UserDetail) =
        githubRepository.insertUserDetail(userDetail)

    override suspend fun deleteUsers() = githubRepository.deleteUsers()

    override suspend fun deleteUserDetail() = githubRepository.deleteUserDetail()

    override fun searchUsers(query: String): Flow<ViewResource<List<User>>> =
        githubRepository.searchUsers(query)

    override fun getFollowers(login: String): Flow<ViewResource<List<User>>> =
        githubRepository.getFollowers(login)

    override fun getFollowings(login: String): Flow<ViewResource<List<User>>> =
        githubRepository.getFollowings(login)

    override fun getThemeSetting(): Flow<Boolean> = githubRepository.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        githubRepository.saveThemeSetting(isDarkModeActive)
}