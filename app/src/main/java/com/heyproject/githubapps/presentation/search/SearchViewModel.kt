package com.heyproject.githubapps.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : ViewModel() {
    fun searchUser(query: String): LiveData<ViewResource<List<User>>> =
        githubUseCase.searchUsers(query).asLiveData()
}