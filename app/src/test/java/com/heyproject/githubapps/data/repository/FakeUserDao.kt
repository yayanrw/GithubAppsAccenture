package com.heyproject.githubapps.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.heyproject.githubapps.data.datasource.local.dao.UserDao
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
Written by Yayan Rahmat Wijaya on 11/15/2022 07:26
Github : https://github.com/yayanrw
 **/

class FakeUserDao : UserDao {
    private var users = mutableListOf<UserEntity>()

    override suspend fun insertUser(user: UserEntity) {
        users.add(user)
    }

    override fun getUsers(): PagingSource<Int, UserEntity> {
        return FakePagingSource(users)
    }

    override fun getUser(login: String): Flow<UserEntity> {
        return flow {
            emit(users.first())
        }
    }

    override fun updateUser(user: UserEntity) {
        TODO()
    }

    override suspend fun deleteUsers() {
        users.clear()
    }

    override fun searchUser(query: String): Flow<List<UserEntity>> {
        return flow {
            emit(users.filter { it.login == query })
        }
    }

    class FakePagingSource(private val data: MutableList<UserEntity>) :
        PagingSource<Int, UserEntity>() {
        @Suppress("SameReturnValue")
        override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int = 0

        override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, UserEntity> {
            return PagingSource.LoadResult.Page(data, 0, 1)
        }

    }
}