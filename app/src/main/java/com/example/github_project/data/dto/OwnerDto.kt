package com.example.github_project.data.dto

import com.google.gson.annotations.SerializedName

data class OwnerDto (
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)