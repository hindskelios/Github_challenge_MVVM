package com.example.github_project.data.dto

import com.google.gson.annotations.SerializedName

data class RepoDto (
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("owner")
    val owner: OwnerDto
)

