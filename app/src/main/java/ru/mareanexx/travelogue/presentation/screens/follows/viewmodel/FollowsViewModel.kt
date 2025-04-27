package ru.mareanexx.travelogue.presentation.screens.follows.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowUserRequest
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.follows.entity.Follows
import ru.mareanexx.travelogue.domain.follows.usecase.FollowUserUseCase
import ru.mareanexx.travelogue.domain.follows.usecase.GetFollowersUseCase
import ru.mareanexx.travelogue.domain.follows.usecase.UnfollowUserUseCase
import javax.inject.Inject

sealed class FollowsUiState {
    data object IsLoading : FollowsUiState()
    data object Success : FollowsUiState()
    data class Error(val message: String) : FollowsUiState()
}

sealed class FollowsEvent {
    data class ShowToast(val message: String) : FollowsEvent()
}

@HiltViewModel
class FollowsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase
): ViewModel() {
    private val _profileId: Int = savedStateHandle["profileId"] ?: -1

    private val _uiState = MutableStateFlow<FollowsUiState>(FollowsUiState.IsLoading)
    val uiState : StateFlow<FollowsUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<FollowsEvent>()
    val eventFlow: SharedFlow<FollowsEvent> = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _followsData = MutableStateFlow(
        FollowersAndFollowingsResponse(followers = emptyList(), followings = emptyList())
    )
    val followsData: StateFlow<FollowersAndFollowingsResponse> = _followsData.asStateFlow()

    init { loadFollows() }

    fun retry() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadFollows()
            _isRefreshing.value = false
        }
    }

    private fun loadFollows() {
        viewModelScope.launch {
            getFollowersUseCase(_profileId)
                .catch { exception -> _uiState.value = FollowsUiState.Error(exception.message ?: "Unknown error") }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { _eventFlow.emit(FollowsEvent.ShowToast(baseResult.error)) }
                        is BaseResult.Success -> {
                            _followsData.value = baseResult.data
                            _uiState.value = FollowsUiState.Success
                        }
                    }
                }
        }
    }

    fun followUser(follow: Follows) {
        viewModelScope.launch {
            followUserUseCase(FollowUserRequest(followerId = _profileId, followingId = follow.id))
                .catch { exception -> _uiState.value = FollowsUiState.Error(exception.message ?: "Unknown error") }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { _eventFlow.emit(FollowsEvent.ShowToast(baseResult.error)) }
                        is BaseResult.Success -> {
                            val newFollowVer = follow.copy(isFollowingBack = true)

                            _followsData.value = _followsData.value.copy(
                                followings = _followsData.value.followings + newFollowVer,
                                followers = _followsData.value.followers.map {
                                    if (it.id == follow.id) newFollowVer else it
                                }
                            )
                            _uiState.value = FollowsUiState.Success
                        }
                    }
                }
        }
    }

    fun unfollowUser(follow: Follows) {
        viewModelScope.launch {
            unfollowUserUseCase(FollowUserRequest(followerId = _profileId, followingId = follow.id))
                .catch { exception -> _uiState.value = FollowsUiState.Error(exception.message ?: "Unknown error") }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { _eventFlow.emit(FollowsEvent.ShowToast(baseResult.error)) }
                        is BaseResult.Success -> {
                            _followsData.value = _followsData.value.copy(
                                followers = _followsData.value.followers.map {
                                    if (it.id == follow.id) it.copy(isFollowingBack = false) else it
                                },
                                followings = _followsData.value.followings.filter { it.id != follow.id }
                            )
                            _uiState.value = FollowsUiState.Success
                        }
                    }
                }
        }
    }
}