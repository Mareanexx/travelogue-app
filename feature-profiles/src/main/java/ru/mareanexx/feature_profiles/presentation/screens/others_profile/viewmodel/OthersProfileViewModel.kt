package ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.feature_profiles.domain.profile.entity.ProfileWithTrips
import ru.mareanexx.feature_profiles.domain.profile.usecase.GetOthersProfileUseCase
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.event.OthersProfileEvent
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.state.FollowingState
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.state.OthersProfileUiState
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

@HiltViewModel
class OthersProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOthersProfileUseCase: GetOthersProfileUseCase,
    private val followUserUseCase: ru.mareanexx.network.domain.follows.usecase.FollowUserUseCase,
    private val unfollowUserUseCase: ru.mareanexx.network.domain.follows.usecase.UnfollowUserUseCase,
    private val reportUseCase: ru.mareanexx.network.domain.report.usecase.CreateReportUseCase
) : ViewModel() {
    private val profileId = savedStateHandle["profileId"] ?: -1

    private val _isFollowing = MutableStateFlow(FollowingState())
    val isFollowing = _isFollowing.asStateFlow()

    private val _uiState = MutableStateFlow<OthersProfileUiState>(OthersProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<OthersProfileEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private fun setLoadingState() { _uiState.value = OthersProfileUiState.Loading }
    private fun setErrorState() { _uiState.value = OthersProfileUiState.Error }
    private fun setSuccessState(data: ProfileWithTrips) { _uiState.value = OthersProfileUiState.Success(data) }

    fun showNotImplementedToast() {
        viewModelScope.launch {
            _eventFlow.emit(OthersProfileEvent.ShowNotImplementedToast)
        }
    }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(OthersProfileEvent.ShowErrorToast(message ?: "Unknown error"))
        }
    }

    init { loadProfile() }

    fun retry() { loadProfile() }
    fun refresh() { loadProfile() }

    private fun loadProfile() {
        viewModelScope.launch {
            getOthersProfileUseCase(profileId)
                .onStart { setLoadingState() }
                .catch { setErrorState() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorState() }
                        is BaseResult.Success -> {
                            _isFollowing.value = FollowingState(
                                isAuthorFollowing = baseResult.data.profile.isFollowing,
                                followersCounter = baseResult.data.profile.followersNumber
                            )
                            setSuccessState(baseResult.data)
                        }
                    }
                }
        }
    }

    fun followUser(profileId: Int) {
        viewModelScope.launch {
            followUserUseCase(profileId)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            _isFollowing.value = FollowingState(
                                isAuthorFollowing = true,
                                followersCounter = _isFollowing.value.followersCounter + 1
                            )
                        }
                    }
                }
        }
    }

    fun unfollowUser(profileId: Int) {
        viewModelScope.launch {
            unfollowUserUseCase(profileId)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            _isFollowing.value = FollowingState(
                                isAuthorFollowing = false,
                                followersCounter = _isFollowing.value.followersCounter - 1
                            )
                        }
                    }
                }
        }
    }

    fun createReport(tripId: Int) {
        viewModelScope.launch {
            reportUseCase(tripId)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> { showToast(baseResult.data) }
                    }
                }
        }
    }
}