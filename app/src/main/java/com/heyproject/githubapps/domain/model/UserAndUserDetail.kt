package com.heyproject.githubapps.domain.model

/**
Written by Yayan Rahmat Wijaya on 11/14/2022 22:15
Github : https://github.com/yayanrw
 **/

data class UserAndUserDetail(
    val user: User, val userDetail: UserDetail
)

//fun UserAndUserDetailEntity.toEntity() : UserAndUserDetail {
//    return UserAndUserDetail(
//        user = User(),
//        userDetail = userD
//    )
//}