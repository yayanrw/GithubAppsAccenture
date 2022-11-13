package com.heyproject.githubapps.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return githubUseCase.getThemeSetting().asLiveData()
    }

    fun saveThemeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            githubUseCase.saveThemeSetting(isDarkModeActive)
        }
    }
}