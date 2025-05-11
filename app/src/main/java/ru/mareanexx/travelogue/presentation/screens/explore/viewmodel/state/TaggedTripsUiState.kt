package ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.state

import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip

sealed class TaggedTripsUiState {
    data object Loading: TaggedTripsUiState()
    data class Success(val tripsData: List<TrendingTrip>) : TaggedTripsUiState()
    data object Error: TaggedTripsUiState()
}