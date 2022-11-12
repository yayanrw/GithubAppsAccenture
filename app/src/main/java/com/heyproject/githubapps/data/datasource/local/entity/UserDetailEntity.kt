package com.heyproject.githubapps.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 00:43
Github : https://github.com/yayanrw
 **/

@Entity(tableName = "user_detail")
data class UserDetailEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val name: String?,
    val bio: String?,
    val blog: String?,
    val company: String?,
    val url: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    val followers: Int?,
    val following: Int?,
    @ColumnInfo(name = "public_repos") val publicRepos: Int?
)