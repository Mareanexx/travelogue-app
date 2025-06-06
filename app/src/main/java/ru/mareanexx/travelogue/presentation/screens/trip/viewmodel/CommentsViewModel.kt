package ru.mareanexx.travelogue.presentation.screens.trip.viewmodel

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
import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorCommentSender
import ru.mareanexx.travelogue.domain.comments.entity.Comment
import ru.mareanexx.travelogue.domain.comments.usecase.AddNewCommentUseCase
import ru.mareanexx.travelogue.domain.comments.usecase.GetCommentsUseCase
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state.CommentsUiState
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val addNewCommentUseCase: AddNewCommentUseCase
): ViewModel() {

    private val _mapPointId = MutableStateFlow(-1)

    private val _authorData = MutableStateFlow(AuthorCommentSender())
    val authorData = _authorData.asStateFlow()

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

    fun onCheckIfCommentIsOthers(senderProfileId: Int) = senderProfileId != _authorData.value.id

    fun retry() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadComments(_mapPointId.value)
            _isRefreshing.value = false
        }
    }

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
                            _commentsData.value = baseResult.data.comments.sortedByDescending { it.sendDate }
                            _authorData.value = baseResult.data.author
                            setSuccessState()
                        }
                    }
                }
        }
    }

    private fun validateSending() = _commentMessage.value.isNotBlank()

    fun addNewComment() {
        viewModelScope.launch {
            if (!validateSending()) return@launch
            addNewCommentUseCase(NewCommentRequest(mapPointId = _mapPointId.value, text = _commentMessage.value, sendDate = OffsetDateTime.now()))
                .catch { setErrorState() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorState() }
                        is BaseResult.Success -> {
                            _commentsData.value = buildList {
                                add(0, baseResult.data.toEntity(authorData.value))
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