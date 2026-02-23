// In data/repository/TrendingRepoImpl.kt
package com.example.github_project.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.github_project.data.remote.GitHubApi
import com.example.github_project.data.paging.TrendingReposPagingSource
import com.example.github_project.domain.model.Repo
import com.example.github_project.domain.repository.TrendingRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val apiService: GitHubApi
) : TrendingRepository {

    override fun getTrendingRepos(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { TrendingReposPagingSource(apiService) }
        ).flow
    }
}
