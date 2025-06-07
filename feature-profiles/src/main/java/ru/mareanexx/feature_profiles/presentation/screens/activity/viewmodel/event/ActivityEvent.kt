package ru.mareanexx.feature_profiles.presentation.screens.activity.viewmodel.event

sealed class ActivityEvent {
    data class ShowToast(val message: String) : ActivityEvent()
}