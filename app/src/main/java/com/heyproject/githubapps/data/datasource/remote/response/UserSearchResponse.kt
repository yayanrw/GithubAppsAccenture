package com.heyproject.githubapps.data.datasource.remote.response


import com.google.gson.annotations.SerializedName
import com.heyproject.githubapps.data.datasource.remote.dto.UserDto

data class UserSearchResponse(
    @SerializedName("incomplete_results") val incompleteResults: Boolean?,
    @SerializedName("items") val items: List<UserDto?>,
    @SerializedName("total_count") val totalCount: Int
)