package ru.mareanexx.network.domain.follows.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.network.domain.follows.FollowsRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetFollowersUseCase @Inject constructor(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(profileId: String): Flow<BaseResult<FollowersAndFollowingsResponse, String>> {
        return followsRepository.getFollowersAndFollowings(profileId = profileId)
    }
}