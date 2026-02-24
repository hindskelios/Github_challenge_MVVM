package com.example.github_project.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.github_project.domain.model.Repo


interface TrendingRepository {
   // suspend fun getTrendingRepos(): List<Repo>

        //    What if the API returned 1,000 items? the app
        //    would try to download all 1,000, parse them,
        //    and hold them all in memory as one giant List.
     fun getTrendingRepos() : Flow<PagingData<Repo>>
}