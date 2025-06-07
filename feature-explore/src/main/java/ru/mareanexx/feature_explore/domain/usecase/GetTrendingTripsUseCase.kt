package ru.mareanexx.feature_explore.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_explore.domain.ExploreRepository
import ru.mareanexx.feature_explore.domain.entity.TrendingTrip
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetTrendingTripsUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<TrendingTrip>, String>> {
        return exploreRepository.getTrendingTrips()
    }
}