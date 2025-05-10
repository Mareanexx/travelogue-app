package ru.mareanexx.travelogue.domain.comments

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.travelogue.domain.comments.entity.Comment
import ru.mareanexx.travelogue.domain.common.BaseResult

interface CommentsRepository {
    suspend fun getAllComments(mapPointId: Int) : Flow<BaseResult<List<Comment>, String>>
    suspend fun addNewComment(newCommentRequest: NewCommentRequest) : Flow<BaseResult<NewCommentResponse, String>>
}