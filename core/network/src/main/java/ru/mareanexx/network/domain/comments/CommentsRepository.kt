package ru.mareanexx.network.domain.comments

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.network.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.network.domain.comments.entity.CommentsWithAuthor
import ru.mareanexx.network.utils.data.BaseResult

interface CommentsRepository {
    suspend fun getAllComments(mapPointId: Int) : Flow<BaseResult<CommentsWithAuthor, String>>
    suspend fun addNewComment(newCommentRequest: NewCommentRequest) : Flow<BaseResult<NewCommentResponse, String>>
}