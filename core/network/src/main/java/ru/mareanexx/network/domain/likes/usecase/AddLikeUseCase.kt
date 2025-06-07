package ru.mareanexx.network.domain.likes.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.domain.likes.LikesRepository
import javax.inject.Inject

class AddLikeUseCase @Inject constructor(
    private val likesRepository: LikesRepository
) {
    suspend operator fun invoke(mapPointId: Int): Flow<BaseResult<String, String>> {
        return likesRepository.addNew(mapPointId)
    }
}