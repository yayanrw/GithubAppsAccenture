package com.heyproject.githubapps.data.datasource.local.dao

import androidx.room.*
import com.heyproject.githubapps.data.datasource.local.entity.UserDetailEntity
import kotlinx.coroutines.flow.Flow

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 01:21
Github : https://github.com/yayanrw
 **/

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserDetail(user: UserDetailEntity)

    @Query("SELECT * FROM user_detail WHERE login = :login")
    fun getUserDetail(login: String): Flow<UserDetailEntity>

    @Query("SELECT * FROM user_detail WHERE is_favorite = 1")
    fun getFavoriteUsers(): Flow<List<UserDetailEntity>>

    @Query("DELETE FROM user_detail")
    suspend fun deleteUserDetail()

    @Update
    fun updateUser(userDetailEntity: UserDetailEntity)
}