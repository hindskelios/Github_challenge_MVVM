package com.example.github_project.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.github_project.data.mapper.toDomain
import com.example.github_project.data.remote.GitHubApi
import com.example.github_project.domain.model.Repo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TrendingReposPagingSource(
    private val apiService: GitHubApi
) : PagingSource<Int, Repo>() {

    private val thirtyDaysAgo: String = run {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        sdf.format(calendar.time)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val currentPage = params.key ?: 1 // Start at page 1
        return try {
            val response = apiService.searchRepositories(
                query = "created:>$thirtyDaysAgo",
                page = currentPage,
                perPage = 20
            )
            val repos = response.items.map { it.toDomain() } // Convert DTO to Domain model

            LoadResult.Page(
                data = repos,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (repos.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor)
        return page?.prevKey?.plus(1)?: page?.nextKey?.minus(1)
    }
}
