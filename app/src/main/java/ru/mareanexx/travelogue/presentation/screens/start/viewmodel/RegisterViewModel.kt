package ru.mareanexx.travelogue.presentation.screens.start.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.register.usecase.RegisterUseCase
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.AuthUiState
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.RegisterFormState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _formState = MutableStateFlow(RegisterFormState())
    val formState: StateFlow<RegisterFormState> get() = _formState

    private val _registerState = MutableStateFlow<AuthUiState>(AuthUiState.Init)
    val registerState: StateFlow<AuthUiState> get() = _registerState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    fun onEmailChanged(value: String) {
        _registerState.value = AuthUiState.Init
        _formState.value = _formState.value.copy(
            email = value,
            enabledButton = false
        )
        validateEmail()
        onCheckEnableButton()
    }

    fun onPasswordChanged(value: String) {
        _registerState.value = AuthUiState.Init
        _formState.value = _formState.value.copy(
            password = value,
            enabledButton = false
        )
        validatePassword()
        onCheckEnableButton()
    }

    private fun onCheckEnableButton() {
        if (_formState.value.isEmailValid && _formState.value.isPasswordValid) {
            _formState.value = _formState.value.copy(
                enabledButton = true
            )
        }
    }

    private fun validateEmail() {
        val regex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        val isValid = regex.matches(_formState.value.email)
        _formState.value = _formState.value.copy(
            isEmailValid = isValid
        )
    }

    private fun validatePassword() {
        var uppercaseLetter = false
        var lowercaseLetter = false
        var eightChars = false
        var oneDigit = false

        if (_formState.value.password.length >= 8) eightChars = true

        for (char in _formState.value.password) {
            if (char.isDigit()) oneDigit = true
            if (char.isUpperCase()) uppercaseLetter = true
            if (char.isLowerCase()) lowercaseLetter = true
        }

        _formState.value = _formState.value.copy(
            uppercaseLetterState = uppercaseLetter,
            lowercaseLetterState = lowercaseLetter,
            eightCharsState = eightChars,
            oneDigitState = oneDigit,
            isPasswordValid = uppercaseLetter && lowercaseLetter && eightChars && oneDigit
        )
    }

    fun register() {
        val registerRequest = RegisterRequest(_formState.value.email, _formState.value.password)
        viewModelScope.launch {
            registerUseCase(registerRequest)
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
                        is BaseResult.Error -> _registerState.value = AuthUiState.Error
                        is BaseResult.Success -> _registerState.value = AuthUiState.Success(baseResult.data)
                    }
                }
        }
    }

    private fun setLoading(setValue: Boolean) {
        _loadingState.value = setValue
    }

    private fun showToast(message: String) {
        _registerState.value = AuthUiState.ShowToast(message)
    }
}