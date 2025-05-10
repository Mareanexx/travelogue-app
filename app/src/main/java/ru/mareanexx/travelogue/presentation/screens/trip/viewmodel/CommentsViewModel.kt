package ru.mareanexx.travelogue.presentation.screens.trip.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.data.comments.mapper.toEntity
import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.travelogue.domain.comments.entity.Comment
import ru.mareanexx.travelogue.domain.comments.usecase.AddNewCommentUseCase
import ru.mareanexx.travelogue.domain.comments.usecase.GetCommentsUseCase
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state.CommentsUiState
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val addNewCommentUseCase: AddNewCommentUseCase
): ViewModel() {

    private val _mapPointId = MutableStateFlow(-1)
    private val avatar = savedStateHandle["avatar"] ?: ""
    private val username = savedStateHandle["username"] ?: ""

    private val _uiState = MutableStateFlow<CommentsUiState>(CommentsUiState.Init)
    val uiState = _uiState.asStateFlow()

    private val _commentsData = MutableStateFlow<List<Comment>>(emptyList())
    val commentsData = _commentsData.asStateFlow()

    private val _commentMessage = MutableStateFlow("")
    val commentMessage = _commentMessage.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private fun setLoadingState() { _uiState.value = CommentsUiState.Loading }
    private fun setSuccessState() { _uiState.value = CommentsUiState.Success }
    private fun setErrorState() { _uiState.value = CommentsUiState.Error }

    fun onCommentTextChanged(newValue: String) { _commentMessage.value = newValue }

    fun retry() { loadComments(_mapPointId.value) }

    fun loadComments(mapPointId: Int) {
        viewModelScope.launch {
            _mapPointId.value = mapPointId
            getCommentsUseCase(mapPointId)
                .onStart { setLoadingState() }
                .catch { setErrorState() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorState() }
                        is BaseResult.Success -> {
                            _commentsData.value = baseResult.data.sortedByDescending { it.sendDate }
                            setSuccessState()
                        }
                    }
                }
        }
    }

    fun addNewComment() {
        viewModelScope.launch {
            addNewCommentUseCase(NewCommentRequest(mapPointId = _mapPointId.value, text = _commentMessage.value, sendDate = OffsetDateTime.now()))
                .catch { setErrorState() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorState() }
                        is BaseResult.Success -> {
                            _commentsData.value = buildList {
                                add(0, baseResult.data.toEntity(username, avatar))
                                addAll(_commentsData.value)
                            }
                            _commentMessage.value = ""
                            setSuccessState()
                        }
                    }
                }
        }
    }
}