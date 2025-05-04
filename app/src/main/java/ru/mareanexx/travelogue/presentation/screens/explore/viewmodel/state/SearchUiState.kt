package ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.state

sealed class SearchUiState {
    data object Init: SearchUiState()
    data object Loading: SearchUiState()
    data object Success : SearchUiState()
    data object Error: SearchUiState()
}