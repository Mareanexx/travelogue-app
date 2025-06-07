package ru.mareanexx.feature_explore.presentation.search.state

sealed class SearchUiState {
    data object Init: SearchUiState()
    data object Loading: SearchUiState()
    data object Success : SearchUiState()
    data object Error: SearchUiState()
}