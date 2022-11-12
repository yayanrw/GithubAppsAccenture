package com.heyproject.githubapps.domain.model

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 13:31
Github : https://github.com/yayanrw
 **/

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val type: String,
)