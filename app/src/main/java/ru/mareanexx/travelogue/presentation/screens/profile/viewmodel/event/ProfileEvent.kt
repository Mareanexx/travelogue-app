package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event

sealed class ProfileEvent {
    data class ShowToast(val message: String) : ProfileEvent()
    data class ShowDeleteDialog(val id: Int) : ProfileEvent()
    data object ShowEditBottomSheet : ProfileEvent()
    data object CloseEditBottomSheet : ProfileEvent()
}

sealed class AccountEvent {
    data class ShowToast(val message: String) : AccountEvent()
    data object ShowDeleteDialog : AccountEvent()
    data object ReturnToStart : AccountEvent()
}