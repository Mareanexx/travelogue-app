package ru.mareanexx.common.ui.state

sealed class UiState {
    data object Init : UiState()
    data class ShowToast(val message: String) : UiState()
    data object Error : UiState()
    data object Success : UiState()
}