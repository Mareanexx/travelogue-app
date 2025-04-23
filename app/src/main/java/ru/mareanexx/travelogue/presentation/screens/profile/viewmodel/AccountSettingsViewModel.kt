package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.domain.account.usecase.DeleteAccountUseCase
import ru.mareanexx.travelogue.domain.account.usecase.GetAccountInfoUseCase
import ru.mareanexx.travelogue.domain.account.usecase.LogOutUseCase
import ru.mareanexx.travelogue.domain.account.usecase.UpdateAccountEmailUseCase
import ru.mareanexx.travelogue.domain.common.BaseResult
import javax.inject.Inject

sealed class AccountUiState {
    data object Init : AccountUiState()
    data object IsLoading : AccountUiState()
    data class ShowToast(val message: String) : AccountUiState()
    data object SuccessChanges : AccountUiState()
    data object ReturnToStart : AccountUiState()
}

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val updateAccountEmailUseCase: UpdateAccountEmailUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val getAccountInfoUseCase: GetAccountInfoUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Init)
    val uiState: StateFlow<AccountUiState> get() = _uiState

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> get() = _userEmail

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled: StateFlow<Boolean> get() = _buttonEnabled

    init {
        getEmail()
    }

    fun onEmailChanged(newValue: String) {
        _userEmail.value = newValue
        validateEmail()
    }

    private fun validateEmail() {
        val regex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        val isValid = regex.matches(_userEmail.value)
        _buttonEnabled.value = isValid
    }

    private fun showToast(message: String) { _uiState.value = AccountUiState.ShowToast(message) }
    private fun setLoading() { _uiState.value = AccountUiState.IsLoading }

    fun getEmail() {
        viewModelScope.launch {
            getAccountInfoUseCase()
                .catch { exception -> showToast(exception.message.toString()) }
                .collect {
                    _userEmail.value = it
                }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            deleteAccountUseCase()
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message.toString()) }
                .collect { result ->
                    when(result) {
                        is BaseResult.Success -> { _uiState.value = AccountUiState.ReturnToStart }
                        is BaseResult.Error -> { showToast(result.error) }
                    }
                }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message.toString()) }
                .collect { _uiState.value = AccountUiState.ReturnToStart }
        }
    }

    fun updateEmail() {
        viewModelScope.launch {
            updateAccountEmailUseCase(_userEmail.value)
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message.toString()) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Success -> {
                            _uiState.value = AccountUiState.SuccessChanges
                        }
                        is BaseResult.Error -> { showToast(baseResult.error) }
                    }
                }
        }
    }
}