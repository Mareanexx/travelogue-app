package ru.mareanexx.feature_auth.presentation.screens.register.viewmodel.form

data class RegisterFormState(
    val email: String = "",
    val password: String = "",

    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,

    val uppercaseLetterState: Boolean = false,
    val lowercaseLetterState: Boolean = false,
    val eightCharsState: Boolean = false,
    val oneDigitState: Boolean = false,

    val enabledButton: Boolean = false
)