package ru.mareanexx.network.domain.comments.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.network.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.network.domain.comments.CommentsRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class AddNewCommentUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(newCommentRequest: NewCommentRequest): Flow<BaseResult<NewCommentResponse, String>> {
        return commentsRepository.addNewComment(newCommentRequest)
    }
}