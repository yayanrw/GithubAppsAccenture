package com.heyproject.githubapps.data.datasource.remote.api

import com.heyproject.githubapps.common.FollowType
import com.heyproject.githubapps.data.datasource.remote.dto.UserDetailDto
import com.heyproject.githubapps.data.datasource.remote.dto.UserDto
import com.heyproject.githubapps.data.datasource.remote.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
Written by Yayan Rahmat Wijaya on 11/10/2022 23:02
Github : https://github.com/yayanrw
 **/

interface GithubService {
    @GET("search/users?q=")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("q") query: String? = "location%3Aindonesia",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UserDto>

    @GET("users/{login}")
    suspend fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("login") login: String,
    ): UserDetailDto

    @GET("search/users")
    suspend fun getSearchUsers(
        @Header("Authorization") token: String,
        @Query("q") query: String,
    ): UserSearchResponse

    @GET("users/{login}/{followType}")
    suspend fun getUserFollow(
        @Header("Authorization") token: String,
        @Path("login") login: String,
        @Path("followType") followType: FollowType
    ): List<UserDto>
}