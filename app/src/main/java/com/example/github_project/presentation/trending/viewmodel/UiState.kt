package com.example.github_project.presentation.trending.viewmodel

sealed class UiState {
    data object Loading : UiState()
    data object Content : UiState()
    data class Error(val message: String) : UiState()
}