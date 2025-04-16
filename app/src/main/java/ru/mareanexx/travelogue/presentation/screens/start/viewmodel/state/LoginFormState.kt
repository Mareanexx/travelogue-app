package ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val enabledButton: Boolean = false
)