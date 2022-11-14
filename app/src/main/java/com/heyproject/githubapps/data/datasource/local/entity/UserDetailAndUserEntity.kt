package com.heyproject.githubapps.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
Written by Yayan Rahmat Wijaya on 11/14/2022 22:25
Github : https://github.com/yayanrw
 **/

data class UserDetailAndUserEntity(
    @Embedded val userDetailEntity: UserDetailEntity,

    @Relation(
        parentColumn = "login", entityColumn = "login"
    ) val userEntity: UserEntity,
)