package ru.mareanexx.travelogue.domain.comments.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.travelogue.domain.comments.CommentsRepository
import ru.mareanexx.travelogue.domain.common.BaseResult
import javax.inject.Inject

class AddNewCommentUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(newCommentRequest: NewCommentRequest): Flow<BaseResult<NewCommentResponse, String>> {
        return commentsRepository.addNewComment(newCommentRequest)
    }
}