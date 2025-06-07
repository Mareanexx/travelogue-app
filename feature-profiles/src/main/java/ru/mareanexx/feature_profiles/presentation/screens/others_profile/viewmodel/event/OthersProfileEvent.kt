package ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.event

sealed class OthersProfileEvent {
    data object ShowNotImplementedToast : OthersProfileEvent()
    data class ShowErrorToast(val message: String) : OthersProfileEvent()
}