package ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.state

import ru.mareanexx.feature_profiles.domain.profile.entity.ProfileWithTrips

sealed class OthersProfileUiState {
    data object Loading : OthersProfileUiState()
    data object Error : OthersProfileUiState()
    data class Success(val profileWithTrips: ProfileWithTrips) : OthersProfileUiState()
}