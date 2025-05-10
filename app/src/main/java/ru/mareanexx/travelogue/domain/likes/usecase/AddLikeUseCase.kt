package ru.mareanexx.travelogue.domain.likes.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.likes.LikesRepository
import javax.inject.Inject

class AddLikeUseCase @Inject constructor(
    private val likesRepository: LikesRepository
) {
    suspend operator fun invoke(mapPointId: Int): Flow<BaseResult<String, String>> {
        return likesRepository.addNew(mapPointId)
    }
}