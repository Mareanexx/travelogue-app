package ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state

sealed class CommentsUiState {
    data object Init : CommentsUiState()
    data object Loading : CommentsUiState()
    data object Success : CommentsUiState()
    data object Error : CommentsUiState()
}