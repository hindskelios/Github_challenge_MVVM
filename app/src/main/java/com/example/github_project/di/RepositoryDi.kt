package com.example.github_project.di

import com.example.github_project.data.repository.TrendingRepositoryImpl
import com.example.github_project.domain.repository.TrendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryDi {
    @Binds
    @Singleton
    abstract fun bindTrendingRepo(impl: TrendingRepositoryImpl): TrendingRepository

}