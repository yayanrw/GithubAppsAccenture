package com.heyproject.githubapps.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Written by Yayan Rahmat Wijaya on 11/11/2022 01:19
Github : https://github.com/yayanrw
 **/

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val id: Int, val prevKey: Int?, val nextKey: Int?
)