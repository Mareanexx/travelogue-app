package ru.mareanexx.travelogue.presentation.screens.activity.viewmodel.state

import ru.mareanexx.travelogue.data.mappoint.remote.dto.TrendingTripWithPoints

sealed class ActivityUiState {
    data object Loading : ActivityUiState()
    data object Error : ActivityUiState()
    data class Success(val tripsData: List<TrendingTripWithPoints>) : ActivityUiState()
    data object NoFollows : ActivityUiState()
}