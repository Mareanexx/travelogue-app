package ru.mareanexx.feature_auth.presentation.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.common.ui.state.AuthUiState
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.domain.login.usecase.LoginUseCase
import ru.mareanexx.feature_auth.presentation.screens.event.AuthEvent
import ru.mareanexx.feature_auth.presentation.screens.login.viewmodel.form.LoginFormState
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Init)
    val loginState: StateFlow<AuthUiState> get() = _loginState

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow get() = _eventFlow.asSharedFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState: StateFlow<LoginFormState> get() = _formState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    fun onEmailChanged(value: String) {
        _loginState.value = AuthUiState.Init
        _formState.value = _formState.value.copy(
            email = value,
        )
        onCheckButtonEnable()
    }

    fun onPasswordChanged(value: String) {
        _loginState.value = AuthUiState.Init
        _formState.value = _formState.value.copy(
            password = value
        )
        onCheckButtonEnable()
    }

    fun onCheckButtonEnable() {
        _formState.value = _formState.value.copy(
            enabledButton = _formState.value.email.isNotBlank() && _formState.value.password.isNotBlank()
        )
    }

    private fun setError() { _loginState.value = AuthUiState.Error }
    private fun setSuccess() { _loginState.value = AuthUiState.Success }
    private fun setLoading(setValue: Boolean) { _loadingState.value = setValue }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(AuthEvent.ShowToast(message ?: "Unknown message"))
        }
    }

    fun login() {
        val loginRequest = LoginRequest(_formState.value.email, _formState.value.password)

        viewModelScope.launch {
            loginUseCase(loginRequest)
                .onStart { setLoading(true) }
                .catch { exception ->
                    setLoading(false)
                    showToast(exception.message)
                }
                .collect { baseResult ->
                    setLoading(false)
                    when(baseResult) {
                        is BaseResult.Error -> setError()
                        is BaseResult.Success -> setSuccess()
                    }
                }
        }
    }
}