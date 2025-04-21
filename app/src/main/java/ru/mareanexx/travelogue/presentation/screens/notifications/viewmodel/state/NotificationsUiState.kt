package ru.mareanexx.travelogue.presentation.screens.notifications.viewmodel.state

import ru.mareanexx.travelogue.domain.notifications.entity.Notification

sealed class NotificationsUiState {
    data object Loading : NotificationsUiState()
    data class Success(val notifications: List<Notification>) : NotificationsUiState()
    data class Error(val message: String) : NotificationsUiState()
}
