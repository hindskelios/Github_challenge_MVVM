package com.example.github_project.presentation.trending.viewmodel

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}