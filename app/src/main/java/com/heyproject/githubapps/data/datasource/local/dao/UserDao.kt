package com.heyproject.githubapps.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 00:40
Github : https://github.com/yayanrw
 **/

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user ORDER BY created_at asc")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM user WHERE login = :login")
    fun getUser(login: String): Flow<UserEntity>

    @Update
    fun updateUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteUsers()

    @Query("SELECT * FROM user WHERE login LIKE '%' || :query || '%' OR id LIKE '%' || :query || '%'")
    fun searchUser(query: String): Flow<List<UserEntity>>
}