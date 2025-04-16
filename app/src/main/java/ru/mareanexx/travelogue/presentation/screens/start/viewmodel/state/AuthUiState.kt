package ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state

import ru.mareanexx.travelogue.domain.common.AuthEntity

sealed class AuthUiState {
    data object Init : AuthUiState()
    data class ShowToast(val message: String) : AuthUiState()
    data object Error : AuthUiState()
    data class Success(val authEntity: AuthEntity) : AuthUiState()
}