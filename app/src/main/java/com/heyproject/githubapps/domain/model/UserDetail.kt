package com.heyproject.githubapps.domain.model

import android.os.Parcelable
import com.heyproject.githubapps.data.datasource.local.entity.UserDetailEntity
import com.heyproject.githubapps.data.datasource.remote.dto.UserDetailDto
import kotlinx.parcelize.Parcelize

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 13:35
Github : https://github.com/yayanrw
 **/

@Parcelize
data class UserDetail(
    val id: Int,
    val login: String,
    val name: String,
    val bio: String,
    val blog: String,
    val company: String,
    val url: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val publicRepos: Int,
    val location: String
) : Parcelable

fun UserDetailDto.toDomain(): UserDetail {
    return UserDetail(
        id = id,
        login = login,
        name = name.orEmpty(),
        bio = bio.orEmpty(),
        blog = blog.orEmpty(),
        company = company.orEmpty(),
        url = url,
        avatarUrl = avatarUrl.orEmpty(),
        followers = followers ?: 0,
        following = following ?: 0,
        publicRepos = publicRepos ?: 0,
        location = location.orEmpty()
    )
}

fun UserDetailEntity.toDomain(): UserDetail {
    return UserDetail(
        id = id,
        login = login,
        name = name.orEmpty(),
        bio = bio.orEmpty(),
        blog = blog.orEmpty(),
        company = company.orEmpty(),
        url = url,
        avatarUrl = avatarUrl.orEmpty(),
        followers = followers ?: 0,
        following = following ?: 0,
        publicRepos = publicRepos ?: 0,
        location = location.orEmpty()
    )
}

fun UserDetailDto.toEntity(): UserDetailEntity {
    return UserDetailEntity(
        id = id,
        login = login,
        name = name,
        bio = bio,
        blog = blog,
        company = company,
        url = url,
        avatarUrl = avatarUrl,
        followers = followers,
        following = following,
        publicRepos = publicRepos,
        location = location
    )
}

fun UserDetail.toEntity(): UserDetailEntity {
    return UserDetailEntity(
        id = id,
        login = login,
        name = name,
        bio = bio,
        blog = blog,
        company = company,
        url = url,
        avatarUrl = avatarUrl,
        followers = followers,
        following = following,
        publicRepos = publicRepos,
        location = location
    )
}