package com.example.github_project.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.github_project.domain.model.Repo
import com.example.github_project.domain.repository.TrendingRepository
import javax.inject.Inject

class GetTrendingRepoUseCase @Inject constructor(
    private  val repository: TrendingRepository
) {
    operator  fun invoke(): Flow<PagingData<Repo>> {
        return repository.getTrendingRepos()
    }

}