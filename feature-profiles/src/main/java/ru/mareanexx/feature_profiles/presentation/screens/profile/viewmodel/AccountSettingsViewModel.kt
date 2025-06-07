package ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.AccountEvent
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.state.AccountUiState
import ru.mareanexx.network.domain.account.usecase.DeleteAccountUseCase
import ru.mareanexx.network.domain.account.usecase.GetAccountInfoUseCase
import ru.mareanexx.network.domain.account.usecase.LogOutUseCase
import ru.mareanexx.network.domain.account.usecase.UpdateAccountEmailUseCase
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject


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

    private val _eventFlow = MutableSharedFlow<AccountEvent>()
    val eventFlow: SharedFlow<AccountEvent> = _eventFlow.asSharedFlow()

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled: StateFlow<Boolean> get() = _buttonEnabled

    init { getEmail() }

    fun onDeleteAccountClicked() {
        viewModelScope.launch {
            _eventFlow.emit(AccountEvent.ShowDeleteDialog)
        }
    }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(AccountEvent.ShowToast(message ?: "Unknown error"))
        }
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

    private fun setLoading() { _uiState.value = AccountUiState.IsLoading }

    private fun getEmail() {
        viewModelScope.launch {
            getAccountInfoUseCase()
                .catch { exception -> showToast(exception.message) }
                .collect { _userEmail.value = it }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            deleteAccountUseCase()
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message) }
                .collect { result ->
                    when(result) {
                        is BaseResult.Success -> { _eventFlow.emit(AccountEvent.ReturnToStart) }
                        is BaseResult.Error -> { showToast(result.error) }
                    }
                }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message) }
                .collect { _eventFlow.emit(AccountEvent.ReturnToStart) }
        }
    }

    fun updateEmail() {
        viewModelScope.launch {
            updateAccountEmailUseCase(_userEmail.value)
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Success -> {
                            _uiState.value = AccountUiState.SuccessChanges
                        }
                        is BaseResult.Error -> {
                            showToast(baseResult.error)
                            _uiState.value = AccountUiState.Error
                        }
                    }
                }
        }
    }
}