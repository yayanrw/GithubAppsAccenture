package com.heyproject.githubapps.presentation.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import com.heyproject.githubapps.utils.MainDispatcherRule
import com.heyproject.githubapps.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Written by Yayan Rahmat Wijaya on 11/15/2022 06:56
 * Github : https://github.com/yayanrw
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SettingViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var githubUseCase: GithubUseCase
    private lateinit var settingViewModel: SettingViewModel

    @Before
    fun setUp() {
        settingViewModel = SettingViewModel(githubUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `fetchThemeSettings return Boolean`() = runTest {
        runBlocking {
            val expected = MutableLiveData<Boolean>()
            expected.value = false

            Mockito.`when`(githubUseCase.getThemeSetting()).thenReturn(expected.asFlow())

            val actual = settingViewModel.fetchThemeSettings().getOrAwaitValue()

            Mockito.verify(githubUseCase).getThemeSetting()

            assertNotNull(actual)
            assertEquals(
                false, actual
            )
        }
    }
}