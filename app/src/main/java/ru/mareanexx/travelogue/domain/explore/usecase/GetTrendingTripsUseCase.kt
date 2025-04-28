package ru.mareanexx.travelogue.domain.explore.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.ExploreRepository
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import javax.inject.Inject

class GetTrendingTripsUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<TrendingTrip>, String>> {
        return exploreRepository.getTrendingTrips()
    }
}