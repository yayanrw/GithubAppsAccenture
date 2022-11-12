package com.heyproject.githubapps.common

/**
Written by Yayan Rahmat Wijaya on 11/12/2022 17:17
Github : https://github.com/yayanrw
 **/

sealed class ViewResource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : ViewResource<T>(data)
    class Success<T>(data: T?) : ViewResource<T>(data)
    class Error<T>(message: String, data: T? = null) : ViewResource<T>(data, message)
}