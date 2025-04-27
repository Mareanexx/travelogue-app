package ru.mareanexx.travelogue.domain.follows.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowUserRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.follows.FollowsRepository
import javax.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(followUserRequest: FollowUserRequest): Flow<BaseResult<String, String>> {
        return followsRepository.followUser(followUserRequest)
    }
}