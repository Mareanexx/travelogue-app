package ru.mareanexx.feature_explore.presentation.explore.viewmodel.state

import ru.mareanexx.feature_explore.domain.entity.TrendingTrip

sealed class TaggedTripsUiState {
    data object Loading: TaggedTripsUiState()
    data class Success(val tripsData: List<TrendingTrip>) : TaggedTripsUiState()
    data object Error: TaggedTripsUiState()
}