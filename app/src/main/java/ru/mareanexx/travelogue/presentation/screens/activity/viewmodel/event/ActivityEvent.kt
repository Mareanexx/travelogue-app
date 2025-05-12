package ru.mareanexx.travelogue.presentation.screens.activity.viewmodel.event

sealed class ActivityEvent {
    data class ShowToast(val message: String) : ActivityEvent()
}