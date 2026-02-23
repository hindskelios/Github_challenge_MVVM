package com.example.github_project.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResultDto (
    @SerializedName("items")
    val items: List<RepoDto>
)