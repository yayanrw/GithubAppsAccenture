package com.heyproject.githubapps.presentation.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import com.heyproject.githubapps.utils.DataDummy
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
 * Written by Yayan Rahmat Wijaya on 11/15/2022 05:43
 * Github : https://github.com/yayanrw
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var githubUseCase: GithubUseCase
    private lateinit var favoriteViewModel: FavoriteViewModel

    private val dummyResponse = DataDummy.generateDummyUserDetail()

    @Before
    fun setUp() {
        favoriteViewModel = FavoriteViewModel(githubUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `fetchFavoriteUsers return list of UserDetail`() = runTest {
        runBlocking {
            val expected = MutableLiveData<List<UserDetail>>()
            expected.value = dummyResponse

            Mockito.`when`(githubUseCase.getFavoriteUsers()).thenReturn(expected.asFlow())

            val actual = favoriteViewModel.fetchFavoriteUsers().getOrAwaitValue()

            Mockito.verify(githubUseCase).getFavoriteUsers()

            assertNotNull(actual)
            assertEquals(
                dummyResponse.size, actual.size
            )
        }
    }

    @Test
    fun `fetchFavoriteUsers return Null Result`() = runTest {
        runBlocking {
            val expected = MutableLiveData<List<UserDetail>>()
            expected.value = null

            Mockito.`when`(githubUseCase.getFavoriteUsers()).thenReturn(expected.asFlow())

            val actual = favoriteViewModel.fetchFavoriteUsers().getOrAwaitValue()

            Mockito.verify(githubUseCase).getFavoriteUsers()

            assertNull(actual)s
        }
    }
}