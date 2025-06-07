package ru.mareanexx.network.domain.comments.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.domain.comments.CommentsRepository
import ru.mareanexx.network.domain.comments.entity.CommentsWithAuthor
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(mapPointId: Int): Flow<BaseResult<CommentsWithAuthor, String>> {
        return commentsRepository.getAllComments(mapPointId)
    }
}