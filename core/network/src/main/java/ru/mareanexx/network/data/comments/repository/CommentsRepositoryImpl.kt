package ru.mareanexx.network.data.comments.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.network.data.comments.mapper.toCommentSender
import ru.mareanexx.network.data.comments.remote.api.CommentsApi
import ru.mareanexx.network.data.comments.remote.dto.AuthorCommentSender
import ru.mareanexx.network.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.network.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.network.domain.comments.CommentsRepository
import ru.mareanexx.network.domain.comments.entity.CommentsWithAuthor
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val userSession: UserSessionManager,
    private val profileDao: ProfileDao,
    private val commentsApi: CommentsApi
): CommentsRepository {

    override suspend fun getAllComments(mapPointId: Int): Flow<BaseResult<CommentsWithAuthor, String>> {
        return flow {
            val response = commentsApi.getAllByMapPointId(mapPointId)
            if (response.isSuccessful) {
                val commentSender = profileDao.getProfile()?.toCommentSender()
                val result = CommentsWithAuthor(
                    commentSender ?: AuthorCommentSender(), response.body()!!.data!!
                )
                emit(BaseResult.Success(result))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun addNewComment(newCommentRequest: NewCommentRequest): Flow<BaseResult<NewCommentResponse, String>> {
        return flow {
            newCommentRequest.senderProfileId = userSession.getProfileId()
            val response = commentsApi.addNewComment(newCommentRequest)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                data.senderId = userSession.getProfileId()
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }
}