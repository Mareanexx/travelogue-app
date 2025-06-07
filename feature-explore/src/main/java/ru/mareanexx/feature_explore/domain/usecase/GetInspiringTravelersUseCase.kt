package ru.mareanexx.feature_explore.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_explore.domain.ExploreRepository
import ru.mareanexx.feature_explore.domain.entity.InspiringProfile
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetInspiringTravelersUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<InspiringProfile>, String>> {
        return exploreRepository.getInspiringTravelers()
    }
}