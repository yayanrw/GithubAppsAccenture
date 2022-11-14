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

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun fetchUserDetail(login: String): LiveData<ViewResource<UserDetail>> {
        _login.value = login
        return githubUseCase.getUserDetail(login).asLiveData()
    }

    fun fetchFollowers(login: String): LiveData<ViewResource<List<User>>> {
        return githubUseCase.getFollowers(login).asLiveData()
    }

    fun fetchFollowings(login: String): LiveData<ViewResource<List<User>>> {
        return githubUseCase.getFollowings(login).asLiveData()
    }

    fun setUserDetail(userDetail: UserDetail?) {
        _userDetail.value = userDetail!!
    }

    fun setFavorite() {
        val newState = !_isFavorite.value!!
        _isFavorite.value = newState
        githubUseCase.updateUserDetail(_userDetail.value!!, newState)
    }
}