package ru.mareanexx.feature_notifications.presentation.viewmodel

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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.feature_notifications.domain.usecase.DeleteAllNotificationsUseCase
import ru.mareanexx.feature_notifications.domain.usecase.GetNotificationsUseCase
import ru.mareanexx.feature_notifications.presentation.viewmodel.event.NotificationsEvent
import ru.mareanexx.feature_notifications.presentation.viewmodel.state.NotificationsUiState
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val deleteAllNotificationsUseCase: DeleteAllNotificationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotificationsUiState>(NotificationsUiState.Loading)
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<NotificationsEvent>()
    val eventFlow: SharedFlow<NotificationsEvent> = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadNotifications()
    }

    fun onDeleteVariantClicked() {
        viewModelScope.launch {
            _eventFlow.emit(NotificationsEvent.ShowDeleteDialog)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadNotifications()
            _isRefreshing.value = false
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            getNotificationsUseCase()
                .onStart {
                    _uiState.value = NotificationsUiState.Loading
                }
                .catch { exception ->
                    _uiState.value = NotificationsUiState.Error(exception.message ?: "Unknown error")
                    _eventFlow.emit(NotificationsEvent.ShowToast(exception.message ?: "Unexpected error"))
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _uiState.value = NotificationsUiState.Success(result.data)
                        }
                        is BaseResult.Error -> {
                            val errorMsg = result.error.message ?: "Unknown error"
                            _uiState.value = NotificationsUiState.Error(errorMsg)
                            _eventFlow.emit(NotificationsEvent.ShowToast(errorMsg))
                        }
                    }
                }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            deleteAllNotificationsUseCase()
                .onStart { _uiState.value = NotificationsUiState.Loading }
                .catch { exception ->
                    _uiState.value = NotificationsUiState.Error(exception.message ?: "Unknown error")
                    _eventFlow.emit(NotificationsEvent.ShowToast(exception.message ?: "Unexpected error"))
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _uiState.value = NotificationsUiState.Success(emptyList())
                        }
                        is BaseResult.Error -> {
                            val errorMsg = result.error.message ?: "Unknown error"
                            _uiState.value = NotificationsUiState.Error(errorMsg)
                            _eventFlow.emit(NotificationsEvent.ShowToast(errorMsg))
                        }
                    }
                }
        }
    }
}