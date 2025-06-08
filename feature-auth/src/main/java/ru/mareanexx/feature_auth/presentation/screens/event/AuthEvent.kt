package ru.mareanexx.feature_auth.presentation.screens.event

sealed class AuthEvent {
    data class ShowToast(val message: String) : AuthEvent()
}