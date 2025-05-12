package ru.mareanexx.travelogue.presentation.screens.othersprofile.viewmodel.event

sealed class OthersProfileEvent {
    data object ShowNotImplementedToast : OthersProfileEvent()
    data class ShowErrorToast(val message: String) : OthersProfileEvent()
}