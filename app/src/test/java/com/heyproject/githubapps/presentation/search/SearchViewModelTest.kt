package com.heyproject.githubapps.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import com.heyproject.githubapps.utils.DataDummy
import com.heyproject.githubapps.utils.MainDispatcherRule
import com.heyproject.githubapps.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Written by Yayan Rahmat Wijaya on 11/15/2022 04:27
 * Github : https://github.com/yayanrw
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var githubUseCase: GithubUseCase
    private lateinit var searchViewModel: SearchViewModel

    private val dummyResponse = DataDummy.generateDummyUser()
    private val dummyQuery = "1"

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(githubUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `fetchSearchUser Success with Successfully Result`() = runTest {
        runBlocking {
            val expected = MutableLiveData<ViewResource<List<User>>>()
            expected.value = ViewResource.Success(dummyResponse)

            `when`(githubUseCase.searchUsers(dummyQuery)).thenReturn(expected.asFlow())

            val actual = searchViewModel.fetchSearchUser(dummyQuery).getOrAwaitValue()

            Mockito.verify(githubUseCase).searchUsers(dummyQuery)

            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is ViewResource.Success)
            Assert.assertEquals(
                dummyResponse, (actual as ViewResource.Success).data
            )
        }
    }

    @Test
    fun `fetchSearchUser Error with Error Result`() = runTest {
        runBlocking {
            val expected = MutableLiveData<ViewResource<List<User>>>()
            expected.value = ViewResource.Error("Error")

            `when`(githubUseCase.searchUsers(dummyQuery)).thenReturn(expected.asFlow())

            val actual = searchViewModel.fetchSearchUser(dummyQuery).getOrAwaitValue()

            Mockito.verify(githubUseCase).searchUsers(dummyQuery)

            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is ViewResource.Error)
            Assert.assertEquals(
                expected.value?.message, actual.message
            )
        }
    }

    @Test
    fun `fetchSearchUser Success with Empty Result`() = runTest {
        runBlocking {
            val expected = MutableLiveData<ViewResource<List<User>>>()
            expected.value = ViewResource.Success(null)

            `when`(githubUseCase.searchUsers("29329323")).thenReturn(expected.asFlow())

            val actual = searchViewModel.fetchSearchUser("29329323").getOrAwaitValue()

            Mockito.verify(githubUseCase).searchUsers("29329323")

            Assert.assertNull(actual.data)
            Assert.assertTrue(actual is ViewResource.Success)
            Assert.assertEquals(
                null, (actual as ViewResource.Success).data
            )
        }
    }
}