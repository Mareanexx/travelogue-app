package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.state

sealed class ProfileUiState {
    data object IsLoading : ProfileUiState()
    data object Showing : ProfileUiState()
}