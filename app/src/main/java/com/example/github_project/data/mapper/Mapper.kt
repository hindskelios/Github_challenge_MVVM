package com.example.github_project.data.mapper

import com.example.github_project.data.dto.RepoDto
import com.example.github_project.domain.model.Repo

fun RepoDto.toDomain(): Repo =
     Repo(
        name = name,
        description = description ?:"",
        //description = description,
        stars = stars,
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl
    )

