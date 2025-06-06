package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event

import androidx.annotation.StringRes
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileBottomSheetType

sealed class ProfileEvent {
    data class ShowToast(val message: String) : ProfileEvent()
    data class ShowDeleteDialog(val deletedType: DeletedType) : ProfileEvent()
    data class ShowTypifiedBottomSheet(
        val type: ProfileBottomSheetType,
        val showing: Boolean
    ): ProfileEvent()
}

enum class DeletedType(@StringRes val text: Int) {
    Avatar(R.string.your_avatar_variant_del),
    Cover(R.string.your_cover_variant_del)
}

sealed class TripsEvent {
    data class ShowToast(val message: String) : TripsEvent()
    data class ShowDeleteDialog(val id: Int) : TripsEvent()
    data class ShowEditBottomSheet(val showing: Boolean) : TripsEvent()
}

sealed class AccountEvent {
    data class ShowToast(val message: String) : AccountEvent()
    data object ShowDeleteDialog : AccountEvent()
    data object ReturnToStart : AccountEvent()
}