package com.heyproject.githubapps.domain.model

import com.heyproject.githubapps.data.datasource.local.entity.UserEntity
import com.heyproject.githubapps.data.datasource.remote.dto.UserDto

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

fun UserDto.toEntity(): UserEntity {
    return UserEntity(id = id, login = login, avatarUrl = avatarUrl, type = type)
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        login = login,
        avatarUrl = avatarUrl.orEmpty(),
        type = type.orEmpty(),
    )
}