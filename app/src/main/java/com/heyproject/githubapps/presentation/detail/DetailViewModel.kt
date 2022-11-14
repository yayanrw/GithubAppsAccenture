package com.heyproject.githubapps.presentation.detail

import androidx.lifecycle.*
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : ViewModel() {
    private val _login = MutableLiveData<String>()
    val login: LiveData<String> = _login

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    fun fetchUserDetail(login: String): LiveData<ViewResource<UserDetail?>> {
        _login.value = login
        return githubUseCase.getUserDetail(login).asLiveData()
    }

    fun fetchFollowers(login: String): LiveData<ViewResource<List<User>>> {
        return githubUseCase.getFollowers(login).asLiveData()
    }

    fun fetchFollowings(login: String): LiveData<ViewResource<List<User>>> {
        return githubUseCase.getFollowings(login).asLiveData()
    }

    fun fetchUserDetailFlat(login: String) {
        viewModelScope.launch {
            _userDetail.value = githubUseCase.getUserDetailFlat(login).first()
        }
    }

    fun setFavorite(userDetail: UserDetail, newStatus: Boolean) {
        githubUseCase.setUserFavorite(userDetail, newStatus)
    }
}