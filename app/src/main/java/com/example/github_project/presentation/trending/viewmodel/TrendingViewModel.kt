package com.example.github_project.presentation.trending.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.github_project.domain.model.Repo
import com.example.github_project.domain.usecase.GetTrendingRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class TrendingViewModel @Inject constructor(
    useCase: GetTrendingRepoUseCase
) : ViewModel() {

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events

    // Flow PagingData qui vient du UseCase
    val repos: StateFlow<PagingData<Repo>> =
        useCase()
            .cachedIn(viewModelScope)
            .catch { e ->
                _events.emit(UiEvent.ShowToast("Erreur: ${e.message ?: "Unknown"}"))
            }
            // on expose un StateFlow pour l'UI
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        // Comme Paging gère load states, on met juste Content après init.
        _uiState.value = UiState.Content
    }

}