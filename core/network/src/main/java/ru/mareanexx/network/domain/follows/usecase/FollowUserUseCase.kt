package ru.mareanexx.network.domain.follows.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.domain.follows.FollowsRepository
import javax.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(followingId: Int): Flow<BaseResult<String, String>> {
        return followsRepository.followUser(followingId)
    }
}