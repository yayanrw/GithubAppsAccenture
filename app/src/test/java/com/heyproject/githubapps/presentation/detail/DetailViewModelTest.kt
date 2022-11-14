package com.heyproject.githubapps.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import com.heyproject.githubapps.utils.DataDummy
import com.heyproject.githubapps.utils.MainDispatcherRule
import com.heyproject.githubapps.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
 * Written by Yayan Rahmat Wijaya on 11/15/2022 06:26
 * Github : https://github.com/yayanrw
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var githubUseCase: GithubUseCase
    private lateinit var detailViewModel: DetailViewModel

    private val dummyUserDetail = DataDummy.generateDummyUserDetail()
    private val dummyLogin = "yayan"

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(githubUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `fetchUserDetail Success with Successfully Result`() = runTest {
        val expected = MutableLiveData<ViewResource<UserDetail?>>()
        expected.value = ViewResource.Success(dummyUserDetail)

        Mockito.`when`(githubUseCase.getUserDetail(dummyLogin)).thenReturn(expected.asFlow())

        val actual = detailViewModel.fetchUserDetail(dummyLogin).getOrAwaitValue()

        Mockito.verify(githubUseCase).getUserDetail(dummyLogin)

        assertNotNull(actual)
        assertTrue(actual is ViewResource.Success)
        assertEquals(
            dummyUserDetail, (actual as ViewResource.Success).data
        )
    }

    @Test
    fun `fetchUserDetail Error with Error Result`() = runTest {
        val expected = MutableLiveData<ViewResource<UserDetail?>>()
        expected.value = ViewResource.Error("Error")

        Mockito.`when`(githubUseCase.getUserDetail(dummyLogin)).thenReturn(expected.asFlow())

        val actual = detailViewModel.fetchUserDetail(dummyLogin).getOrAwaitValue()

        Mockito.verify(githubUseCase).getUserDetail(dummyLogin)

        assertNotNull(actual)
        assertTrue(actual is ViewResource.Error)
        assertEquals(
            expected.value?.message, actual.message
        )
    }
}