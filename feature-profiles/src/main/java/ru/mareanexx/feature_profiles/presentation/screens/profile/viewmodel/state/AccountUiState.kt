package ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.state

sealed class AccountUiState {
    data object Init : AccountUiState()
    data object IsLoading : AccountUiState()
    data object SuccessChanges : AccountUiState()
    data object Error : AccountUiState()
}