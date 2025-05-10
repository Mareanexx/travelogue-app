package ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state

sealed class TripUiState {
    data object Loading : TripUiState()
    data object Showing : TripUiState()
}