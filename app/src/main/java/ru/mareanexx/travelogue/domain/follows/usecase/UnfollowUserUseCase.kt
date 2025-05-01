package ru.mareanexx.travelogue.domain.follows.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.follows.FollowsRepository
import javax.inject.Inject

class UnfollowUserUseCase @Inject constructor(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(followingId: Int): Flow<BaseResult<String, String>> {
        return followsRepository.unfollowUser(followingId)
    }
}