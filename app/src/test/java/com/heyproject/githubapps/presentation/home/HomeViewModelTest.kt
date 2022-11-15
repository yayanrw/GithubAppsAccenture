package com.heyproject.githubapps.presentation.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.usecase.GithubUseCase
import com.heyproject.githubapps.presentation.adapter.UserAdapter
import com.heyproject.githubapps.utils.DataDummy
import com.heyproject.githubapps.utils.MainDispatcherRule
import com.heyproject.githubapps.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Written by Yayan Rahmat Wijaya on 11/15/2022 07:01
 * Github : https://github.com/yayanrw
 */

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var githubUseCase: GithubUseCase
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var context: Context

    private val dummyUsers = DataDummy.generateDummyUsers()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(githubUseCase)
        context = Mockito.mock(Context::class.java)
    }

    @Test
    fun `Get users pagination with success result`() = runTest {
        val data: PagingData<User> = UserPagingSource.snapshot(dummyUsers)

        val expected = MutableLiveData<PagingData<User>>()
        expected.value = data

        `when`(githubUseCase.getUsers()).thenReturn(expected)

        val homeViewModel = HomeViewModel(githubUseCase)
        val actualResponse: PagingData<User> = homeViewModel.fetchUsers().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = UserAdapter.DiffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualResponse)

        assertNotNull(differ.snapshot())
        assertEquals(dummyUsers, differ.snapshot())
        assertEquals(dummyUsers.size, differ.snapshot().size)
        assertEquals(dummyUsers[0].login, differ.snapshot()[0]?.login)
    }
}

class UserPagingSource : PagingSource<Int, LiveData<List<User>>>() {
    companion object {
        fun snapshot(items: List<User>): PagingData<User> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<User>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<User>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}