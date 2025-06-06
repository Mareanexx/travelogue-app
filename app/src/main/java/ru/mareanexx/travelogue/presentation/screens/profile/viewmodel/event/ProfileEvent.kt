package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event

import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileBottomSheetType

sealed class ProfileEvent {
    data class ShowToast(val message: String) : ProfileEvent()
    data class ShowDeleteDialog(val deletedType: DeletedType) : ProfileEvent()
    data class ShowTypifiedBottomSheet(
        val type: ProfileBottomSheetType,
        val showing: Boolean
    ): ProfileEvent()
}

sealed class TripsEvent {
    data class ShowToast(val message: String) : TripsEvent()
    data class ShowTypifiedDialog(val id: Int, val type: TripTypifiedDialog) : TripsEvent()
    data class ShowEditBottomSheet(val showing: Boolean) : TripsEvent()
}

sealed class AccountEvent {
    data class ShowToast(val message: String) : AccountEvent()
    data object ShowDeleteDialog : AccountEvent()
    data object ReturnToStart : AccountEvent()
}