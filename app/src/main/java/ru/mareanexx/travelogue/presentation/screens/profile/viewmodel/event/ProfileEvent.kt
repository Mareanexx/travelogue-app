package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event

sealed class ProfileEvent {
    data class ShowToast(val message: String) : ProfileEvent()
}