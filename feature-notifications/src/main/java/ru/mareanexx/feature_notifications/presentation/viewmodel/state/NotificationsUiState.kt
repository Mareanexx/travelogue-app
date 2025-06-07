package ru.mareanexx.feature_notifications.presentation.viewmodel.state

import ru.mareanexx.feature_notifications.domain.entity.Notification

sealed class NotificationsUiState {
    data object Loading : NotificationsUiState()
    data class Success(val notifications: List<Notification>) : NotificationsUiState()
    data class Error(val message: String) : NotificationsUiState()
}
