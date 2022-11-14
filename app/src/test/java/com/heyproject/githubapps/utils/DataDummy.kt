package com.heyproject.githubapps.utils

import com.heyproject.githubapps.domain.model.User
import com.heyproject.githubapps.domain.model.UserDetail
import kotlin.random.Random

/**
Written by Yayan Rahmat Wijaya on 11/15/2022 04:28
Github : https://github.com/yayanrw
 **/

object DataDummy {
    fun generateDummyUsers(): List<User> {
        val users = arrayListOf<User>()
        for (i in 0..10) {
            val user = User(
                i, "login$i", "avatarUrl$i", "type$i"
            )
            users.add(user)
        }
        return users
    }

    fun generateDummyUserDetails(): List<UserDetail> {
        val userDetails = arrayListOf<UserDetail>()
        for (i in 0..10) {
            val userDetail = UserDetail(
                i,
                "login$i",
                "name$i",
                "bio$i",
                "blog$i",
                "company$i",
                "url$i",
                "avatarUrl$i",
                Random.nextInt(),
                Random.nextInt(),
                Random.nextInt(),
                "location$i",
                Random.nextBoolean()
            )
            userDetails.add(userDetail)
        }
        return userDetails
    }

    fun generateDummyUserDetail(): UserDetail {
        return UserDetail(
            Random.nextInt(),
            "yayan",
            "name ${Random.nextInt()}",
            "bio ${Random.nextInt()}",
            "blog ${Random.nextInt()}",
            "company ${Random.nextInt()}",
            "url ${Random.nextInt()}",
            "avatarUrl ${Random.nextInt()}",
            Random.nextInt(),
            Random.nextInt(),
            Random.nextInt(),
            "location ${Random.nextInt()}",
            Random.nextBoolean()
        )
    }
}