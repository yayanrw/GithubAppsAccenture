package com.heyproject.githubapps.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.heyproject.githubapps.data.datasource.local.entity.UserDetailEntity
import com.heyproject.githubapps.data.datasource.remote.dto.UserDetailDto

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 01:21
Github : https://github.com/yayanrw
 **/

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(user: UserDetailEntity)

    @Query("SELECT * FROM user_detail WHERE login = :login")
    fun getUserDetail(login: String): UserDetailDto

    @Query("DELETE FROM user_detail")
    fun deleteAll()
}