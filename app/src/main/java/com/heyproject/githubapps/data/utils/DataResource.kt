package com.heyproject.githubapps.data.utils

/**
Written by Yayan Rahmat Wijaya on 11/10/2022 23:42
Github : https://github.com/yayanrw
 **/

sealed class DataResource<out R> {
    data class Success<out T>(val data: T) : DataResource<T>()
    data class Error(val errorMessage: String) : DataResource<Nothing>()
    object Empty : DataResource<Nothing>()
}