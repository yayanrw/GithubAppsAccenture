package com.heyproject.githubapps.data.utils

/**
Written by Yayan Rahmat Wijaya on 11/10/2022 23:42
Github : https://github.com/yayanrw
 **/

sealed class DataResource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : DataResource<T>(data)
    class Success<T>(data: T?) : DataResource<T>(data)
    class Error<T>(message: String, data: T? = null) : DataResource<T>(data, message)
}