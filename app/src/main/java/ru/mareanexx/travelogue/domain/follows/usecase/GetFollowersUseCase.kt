package ru.mareanexx.travelogue.domain.follows.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.follows.FollowsRepository
import javax.inject.Inject

class GetFollowersUseCase @Inject constructor(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(profileId: String): Flow<BaseResult<FollowersAndFollowingsResponse, String>> {
        return followsRepository.getFollowersAndFollowings(profileId = profileId)
    }
}