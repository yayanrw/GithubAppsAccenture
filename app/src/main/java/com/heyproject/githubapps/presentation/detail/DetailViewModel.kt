package com.heyproject.githubapps.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : ViewModel() {
    private val _login = MutableLiveData<String>()
    val login: LiveData<String> = _login

    fun getUserDetail(login: String): LiveData<ViewResource<UserDetail>> {
        _login.value = login
        return githubUseCase.getUserDetail(login).asLiveData()
    }

    fun getFollowers(login: String): LiveData<ViewResource<List<User>>> {
        return githubUseCase.getFollowers(login).asLiveData()
    }

    fun getFollowings(login: String): LiveData<ViewResource<List<User>>> {
        return githubUseCase.getFollowings(login).asLiveData()
    }

}