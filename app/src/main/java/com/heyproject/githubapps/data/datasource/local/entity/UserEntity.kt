package com.heyproject.githubapps.data.datasource.local.entity

import androidx.room.*

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 00:42
Github : https://github.com/yayanrw
 **/

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    val type: String?,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

@Entity
data class UserAndUserDetailEntity(
    @Embedded val userEntity: UserEntity,

    @Relation(
        parentColumn = "login", entityColumn = "login"
    ) val userDetailEntity: UserDetailEntity
)
