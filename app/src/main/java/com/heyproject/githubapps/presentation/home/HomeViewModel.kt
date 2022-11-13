package com.heyproject.githubapps.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import javax.inject.Singleton

@Singleton
class HomeViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun fetchUsers(): LiveData<PagingData<User>> = githubUseCase.getUsers().cachedIn(viewModelScope)
}