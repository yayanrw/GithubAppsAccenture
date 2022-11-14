package com.heyproject.githubapps.presentation.favorite

import androidx.lifecycle.ViewModel
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : ViewModel() {
}