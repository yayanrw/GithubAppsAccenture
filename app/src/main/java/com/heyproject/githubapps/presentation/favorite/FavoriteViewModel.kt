package com.heyproject.githubapps.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : ViewModel() {
    fun fetchFavoriteUsers(): LiveData<List<UserDetail>> =
        githubUseCase.getFavoriteUsers().asLiveData()
}