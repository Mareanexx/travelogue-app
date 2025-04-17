package ru.mareanexx.travelogue.presentation.screens.start.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.data.login.remote.dto.LoginRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.login.usecase.LoginUseCase
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.LoginFormState
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.UiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _loginState = MutableStateFlow<UiState>(UiState.Init)
    val loginState: StateFlow<UiState> get() = _loginState

    private val _formState = MutableStateFlow(LoginFormState())
    val formState: StateFlow<LoginFormState> get() = _formState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    fun onEmailChanged(value: String) {
        _loginState.value = UiState.Init
        _formState.value = _formState.value.copy(
            email = value,
        )
    }

    fun onPasswordChanged(value: String) {
        _loginState.value = UiState.Init
        _formState.value = _formState.value.copy(
            password = value
        )
    }

    fun onCheckButtonEnable() {
        _formState.value = _formState.value.copy(
            enabledButton = _formState.value.email.isNotBlank() && _formState.value.password.isNotBlank()
        )
    }

    private fun setLoading(setValue: Boolean) { _loadingState.value = setValue }

    private fun showToast(message: String) {
        _loginState.value = UiState.ShowToast(message)
    }

    fun login() {
        val loginRequest = LoginRequest(_formState.value.email, _formState.value.password)

        viewModelScope.launch {
            loginUseCase(loginRequest)
                .onStart {
                    setLoading(true)
                }
                .catch { exception ->
                    setLoading(false)
                    showToast(exception.message.toString())
                }
                .collect { baseResult ->
                    setLoading(false)
                    when(baseResult) {
                        is BaseResult.Error -> _loginState.value = UiState.Error
                        is BaseResult.Success -> _loginState.value = UiState.Success
                    }
                }
        }
    }
}