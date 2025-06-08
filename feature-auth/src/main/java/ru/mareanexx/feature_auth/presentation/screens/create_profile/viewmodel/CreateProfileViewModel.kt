package ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel

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
import ru.mareanexx.feature_auth.data.profile.mapper.toRequest
import ru.mareanexx.feature_auth.domain.profile.usecase.UploadProfileUseCase
import ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form.CreateProfileForm
import ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form.ProfileField
import ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form.ValidationErrorType
import ru.mareanexx.feature_auth.presentation.screens.event.AuthEvent
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val uploadProfileUseCase: UploadProfileUseCase
): ViewModel() {
    private val _formState = MutableStateFlow(CreateProfileForm())
    val formState: StateFlow<CreateProfileForm> get() = _formState

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow get() = _eventFlow.asSharedFlow()

    private val _profileState = MutableStateFlow<AuthUiState>(AuthUiState.Init)
    val profileState: StateFlow<AuthUiState> get() = _profileState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private fun setValidationError(fieldsError: Map<ProfileField, ValidationErrorType>) { _formState.value = _formState.value.copy(fieldsError = fieldsError) }
    private fun removeValidationError(key: ProfileField) {
        if (_formState.value.fieldsError.isNotEmpty()) {
            val fieldErrors: MutableMap<ProfileField, ValidationErrorType> = _formState.value.fieldsError.toMutableMap()
            fieldErrors.remove(key)
            setValidationError(fieldErrors)
        }
    }

    private fun validateUsername() = _formState.value.username.length >= 3

    private fun validateFullname(): Boolean {
        val regex = Regex("^[a-zA-Zа-яА-Я]+\\s[a-zA-Zа-яА-Я]+\$")
        return regex.matches(_formState.value.fullname)
    }

    private fun validateAll(): Boolean {
        val fieldErrors = mutableMapOf<ProfileField, ValidationErrorType>()
        if (!validateUsername()) fieldErrors[ProfileField.Username] = ValidationErrorType.TOO_SHORT_USERNAME
        if (!validateFullname()) fieldErrors[ProfileField.Fullname] = ValidationErrorType.TOO_SHORT_FULLNAME

        return if (fieldErrors.isEmpty()) { true } else { setValidationError(fieldErrors); false }

    }

    fun onFullnameChanged(newValue: String) {
        _formState.value = _formState.value.copy(fullname = newValue)
        removeValidationError(ProfileField.Fullname)
    }

    fun onUsernameChanged(newValue: String) {
        _formState.value = _formState.value.copy(username = newValue)
        removeValidationError(ProfileField.Username)
    }

    fun onBioChanged(newValue: String) {
        _formState.value = _formState.value.copy(bio = newValue)
    }

    fun onAvatarSelected(newAvatar: File) {
        _formState.value = _formState.value.copy(avatar = newAvatar)
    }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(AuthEvent.ShowToast(message ?: "Unknown message"))
        }
    }

    private fun setLoading(newValue: Boolean) { _loadingState.value = newValue }
    private fun setSuccess() { _profileState.value = AuthUiState.Success }
    private fun setError() { _profileState.value = AuthUiState.Error }

    fun createProfile() {
        viewModelScope.launch {
            if (!validateAll()) return@launch
            uploadProfileUseCase(_formState.value.toRequest(), _formState.value.avatar)
                .onStart { setLoading(true) }
                .catch { exception ->
                    setLoading(false)
                    showToast(exception.message)
                }
                .collect { baseResult ->
                    setLoading(false)
                    when(baseResult) {
                        is BaseResult.Success -> setSuccess()
                        is BaseResult.Error -> setError()
                    }
                }
        }
    }
}