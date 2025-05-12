package ru.mareanexx.travelogue.presentation.screens.othersprofile.viewmodel.state

import ru.mareanexx.travelogue.domain.profile.entity.ProfileWithTrips

sealed class OthersProfileUiState {
    data object Loading : OthersProfileUiState()
    data object Error : OthersProfileUiState()
    data class Success(val profileWithTrips: ProfileWithTrips) : OthersProfileUiState()
}