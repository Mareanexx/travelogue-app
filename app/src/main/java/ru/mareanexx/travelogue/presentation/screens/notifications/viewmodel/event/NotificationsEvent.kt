package ru.mareanexx.travelogue.presentation.screens.notifications.viewmodel.event

sealed class NotificationsEvent {
    data class ShowToast(val message: String) : NotificationsEvent()
}