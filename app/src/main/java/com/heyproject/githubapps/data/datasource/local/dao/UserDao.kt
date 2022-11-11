package com.heyproject.githubapps.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 00:40
Github : https://github.com/yayanrw
 **/

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM user")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user")
    fun deleteAll()

    @Query("SELECT * FROM user WHERE login LIKE '%' || :userName || '%' OR id LIKE '%' || :id || '%'")
    fun searchUser(id: Int, userName: String): Flow<List<UserEntity>>
}