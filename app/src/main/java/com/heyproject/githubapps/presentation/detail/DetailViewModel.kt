package com.heyproject.githubapps.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : ViewModel() {
    fun getUserDetail(login: String): LiveData<ViewResource<UserDetail>> {
        return githubUseCase.getUserDetail(login).asLiveData()
    }

}