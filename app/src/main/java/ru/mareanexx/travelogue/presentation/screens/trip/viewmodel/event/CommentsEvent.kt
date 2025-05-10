package ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event

sealed class CommentsEvent {
    data class ShowToast(val message: String) : CommentsEvent()
}