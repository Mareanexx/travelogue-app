package ru.mareanexx.feature_profiles.presentation.screens.trip.viewmodel.event

sealed class CommentsEvent {
    data class ShowToast(val message: String) : CommentsEvent()
}