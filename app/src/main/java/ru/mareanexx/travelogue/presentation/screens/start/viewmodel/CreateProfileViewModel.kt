package ru.mareanexx.travelogue.presentation.screens.start.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.data.profile.mapper.toRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.usecase.UploadProfileUseCase
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.CreateProfileForm
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.UiState
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val uploadProfileUseCase: UploadProfileUseCase
): ViewModel() {
    private val _formState = MutableStateFlow(CreateProfileForm())
    val formState: StateFlow<CreateProfileForm> get() = _formState

    private val _profileState = MutableStateFlow<UiState>(UiState.Init)
    val profileState: StateFlow<UiState> get() = _profileState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private fun onCheckButtonEnable() {
        _formState.value = _formState.value.copy(
            buttonEnabled = _formState.value.isUsernameValid && _formState.value.isFullnameValid
        )
    }

    private fun validateUsername() {
        _formState.value = _formState.value.copy(
            isUsernameValid = _formState.value.username.length >= 3
        )
    }

    private fun validateFullname() {
        val regex = Regex("^[a-zA-Zа-яА-Я]+\\s[a-zA-Zа-яА-Я]+\$")
        _formState.value = _formState.value.copy(
            isFullnameValid = regex.matches(_formState.value.fullname)
        )
        onCheckButtonEnable()
    }

    fun onFullnameChanged(newValue: String) {
        _formState.value = _formState.value.copy(fullname = newValue)
        validateFullname()
    }

    fun onUsernameChanged(newValue: String) {
        _formState.value = _formState.value.copy(username = newValue)
        validateUsername()
    }

    fun onBioChanged(newValue: String) {
        _formState.value = _formState.value.copy(
            bio = newValue
        )
    }

    fun onAvatarSelected(newAvatar: File) {
        _formState.value = _formState.value.copy(avatar = newAvatar)
    }

    private fun showToast(message: String) { _profileState.value = UiState.ShowToast(message) }

    private fun setLoading(newValue: Boolean) = run { _loadingState.value = newValue }

    fun createProfile() {
        val request = _formState.value.toRequest()
        viewModelScope.launch {
            uploadProfileUseCase(request, _formState.value.avatar)
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
                        is BaseResult.Success -> _profileState.value = UiState.Success
                        is BaseResult.Error -> _profileState.value = UiState.Error
                    }
                }
        }
    }
}