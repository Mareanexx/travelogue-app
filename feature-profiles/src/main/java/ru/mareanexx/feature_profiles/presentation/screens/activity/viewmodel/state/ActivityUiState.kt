package ru.mareanexx.feature_profiles.presentation.screens.activity.viewmodel.state

import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.ActivityTripWithMapPoints

sealed class ActivityUiState {
    data object Loading : ActivityUiState()
    data object Error : ActivityUiState()
    data class Success(val tripsData: List<ActivityTripWithMapPoints>) : ActivityUiState()
    data object NoFollows : ActivityUiState()
}