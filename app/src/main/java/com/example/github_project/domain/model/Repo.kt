package com.example.github_project.domain.model

data class Repo (
    val name : String,
    val description : String,
    val stars: Int,
    val ownerName : String,
    val ownerAvatarUrl : String
)