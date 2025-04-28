package ru.mareanexx.travelogue.domain.explore.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.ExploreRepository
import ru.mareanexx.travelogue.domain.explore.entity.InspiringProfileResponse
import javax.inject.Inject

class GetInspiringTravelersUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<InspiringProfileResponse>, String>> {
        return exploreRepository.getInspiringTravelers()
    }
}