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

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

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

    fun getUser(login: String) {
        viewModelScope.launch {
            _user.value = githubUseCase.getUser(login).first()
        }
    }
}