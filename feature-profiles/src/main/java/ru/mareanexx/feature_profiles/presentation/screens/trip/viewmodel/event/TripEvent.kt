package ru.mareanexx.feature_profiles.presentation.screens.trip.viewmodel.event

enum class DialogType {
    Error, DatePicker, TimePicker, ChooseLocation
}

enum class BottomSheetType {
    AddStep, EditStep
}

sealed class TripEvent {
    data class ShowTypifiedDialog(val type: DialogType) : TripEvent()
    data class ShowTypifiedBottomSheet(val type: BottomSheetType) : TripEvent()
    data class ShowToast(val message: String) : TripEvent()
}