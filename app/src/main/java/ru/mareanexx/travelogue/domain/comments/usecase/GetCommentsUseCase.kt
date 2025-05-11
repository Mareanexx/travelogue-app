package ru.mareanexx.travelogue.domain.comments.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.comments.CommentsRepository
import ru.mareanexx.travelogue.domain.comments.entity.CommentsWithAuthor
import ru.mareanexx.travelogue.domain.common.BaseResult
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(mapPointId: Int): Flow<BaseResult<CommentsWithAuthor, String>> {
        return commentsRepository.getAllComments(mapPointId)
    }
}