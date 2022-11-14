package com.heyproject.githubapps.utils

import com.heyproject.githubapps.domain.model.User

/**
Written by Yayan Rahmat Wijaya on 11/15/2022 04:28
Github : https://github.com/yayanrw
 **/

object DataDummy {
    fun generateDummyUsersEntity(): List<User> {
        val users = arrayListOf<User>()
        for (i in 0..10) {
            val user = User(
                i, "login$i", "avatarUrl$i", "type$i"
            )
            users.add(user)
        }
        return users
    }
}