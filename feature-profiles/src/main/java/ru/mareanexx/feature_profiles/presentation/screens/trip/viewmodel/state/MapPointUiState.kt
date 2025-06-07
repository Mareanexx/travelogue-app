package ru.mareanexx.feature_profiles.presentation.screens.trip.viewmodel.state

sealed class MapPointUiState {
    data object Init : MapPointUiState()
    data object Loading : MapPointUiState()
    data object Success : MapPointUiState()
    data object Error : MapPointUiState()
}