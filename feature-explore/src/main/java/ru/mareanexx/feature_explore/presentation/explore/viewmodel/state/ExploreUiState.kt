package ru.mareanexx.feature_explore.presentation.explore.viewmodel.state

sealed class ExploreUiState {
    data object Loading : ExploreUiState()
    data class Error(val message: String) : ExploreUiState()
    data object Showing : ExploreUiState()
}