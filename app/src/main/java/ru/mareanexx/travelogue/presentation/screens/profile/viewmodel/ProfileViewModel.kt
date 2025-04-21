package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.data.profile.remote.dto.UpdateProfileRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.usecase.GetProfileWithTripsUseCase
import ru.mareanexx.travelogue.domain.profile.usecase.UpdateProfileUseCase
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileSettingsSheet
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.state.UpdateProfileForm
import java.io.File
import javax.inject.Inject

sealed class ProfileUiState {
    data object Init : ProfileUiState()
    data object IsLoading : ProfileUiState()
    data class ShowToast(val message: String) : ProfileUiState()
    data object Showing : ProfileUiState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileWithTripsUseCase: GetProfileWithTripsUseCase,
    private val updateProfile: UpdateProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> get() = _uiState

    private val _profileData = MutableStateFlow<ProfileDto?>(null)
    val profileData: StateFlow<ProfileDto?> get() = _profileData

    private val _sheetType = MutableStateFlow(ProfileSettingsSheet.None)
    val sheetType: StateFlow<ProfileSettingsSheet> get() = _sheetType

    private val _sheetExpandedState = MutableStateFlow(false)
    val sheetExpandedState: StateFlow<Boolean> get() = _sheetExpandedState

    init {
        loadProfile()
    }

    fun closeBottomSheet() { _sheetExpandedState.value = false }

    fun changeBottomSheetType(newType: ProfileSettingsSheet) {
        _sheetExpandedState.value = true
        _sheetType.value = newType
        if (newType == ProfileSettingsSheet.EditProfile) {
            _updatedProfileData.value = UpdateProfileForm(
                username = profileData.value!!.username,
                bio = profileData.value!!.bio,
                fullName = profileData.value!!.fullName
            )
        }
    }

    private fun setLoading() { _uiState.value = ProfileUiState.IsLoading }

    private fun showToast(message: String) { _uiState.value = ProfileUiState.ShowToast(message) }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileWithTripsUseCase()
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message.toString()) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> showToast(baseResult.error)
                        is BaseResult.Success -> {
                            _uiState.value = ProfileUiState.Showing
                            _profileData.value = baseResult.data
                        }
                    }
                }
        }
    }

    private val _updatedProfileData = MutableStateFlow(UpdateProfileForm())
    val updatedProfileData: StateFlow<UpdateProfileForm> get() = _updatedProfileData

    private fun onCheckButtonEnable() {
        _updatedProfileData.value = _updatedProfileData.value.copy(
            buttonEnabled = _updatedProfileData.value.isUsernameValid || _updatedProfileData.value.isFullnameValid
                    || _updatedProfileData.value.avatar != null || _updatedProfileData.value.coverPhoto != null
        )
    }

    private fun validateUsername() {
        _updatedProfileData.value = _updatedProfileData.value.copy(
            isUsernameValid = _updatedProfileData.value.username.length >= 3
        )
        onCheckButtonEnable()
    }

    private fun validateFullname() {
        val regex = Regex("^[a-zA-Zа-яА-Я]+\\s[a-zA-Zа-яА-Я]+\$")
        _updatedProfileData.value = _updatedProfileData.value.copy(
            isFullnameValid = regex.matches(_updatedProfileData.value.fullName)
        )
        onCheckButtonEnable()
    }

    fun onFullnameChanged(newValue: String) {
        _updatedProfileData.value = _updatedProfileData.value.copy(fullName = newValue)
        validateFullname()
    }

    fun onUsernameChanged(newValue: String) {
        _updatedProfileData.value = _updatedProfileData.value.copy(username = newValue)
        validateUsername()
    }

    fun onBioChanged(newValue: String) {
        _updatedProfileData.value = _updatedProfileData.value.copy(
            bio = newValue
        )
    }

    fun onAvatarSelected(newAvatar: File) {
        _updatedProfileData.value = _updatedProfileData.value.copy(avatar = newAvatar)
        onCheckButtonEnable()
    }

    fun onCoverPhotoSelected(newCover: File) {
        _updatedProfileData.value = _updatedProfileData.value.copy(coverPhoto = newCover)
        onCheckButtonEnable()
    }

    fun updateProfile() {
        val updateProfileRequest = UpdateProfileRequest(
            id = profileData.value!!.id,
            username = _updatedProfileData.value.username.ifEmpty { null },
            fullName = _updatedProfileData.value.fullName.ifEmpty { null },
            bio = _updatedProfileData.value.bio.ifEmpty { null },
        )

        viewModelScope.launch {
            updateProfile(updateProfileRequest, _updatedProfileData.value.avatar, _updatedProfileData.value.coverPhoto)
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message.toString()) }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _profileData.value = result.data
                            _uiState.value = ProfileUiState.Showing
                            _sheetExpandedState.value = false
                            _sheetType.value = ProfileSettingsSheet.None
                        }
                        is BaseResult.Error -> {
                            showToast(result.error)
                            _uiState.value = ProfileUiState.Showing
                        }
                    }
                }
        }
    }
}