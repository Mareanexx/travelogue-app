package ru.mareanexx.feature_auth.presentation.screens.login.viewmodel.form

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val enabledButton: Boolean = false
)