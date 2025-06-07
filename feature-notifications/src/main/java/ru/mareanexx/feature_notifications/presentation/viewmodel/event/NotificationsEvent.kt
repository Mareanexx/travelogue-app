package ru.mareanexx.feature_notifications.presentation.viewmodel.event

sealed class NotificationsEvent {
    data class ShowToast(val message: String) : NotificationsEvent()
    data object ShowDeleteDialog : NotificationsEvent()
}