package ru.mareanexx.common.ui.state

sealed class AuthUiState {
    data object Init : AuthUiState()
    data object Error : AuthUiState()
    data object Success : AuthUiState()
}