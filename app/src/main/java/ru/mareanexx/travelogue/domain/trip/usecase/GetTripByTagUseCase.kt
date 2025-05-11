package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.domain.trip.TripRepository
import javax.inject.Inject

class GetTripByTagUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(tagName: String): Flow<BaseResult<List<TrendingTrip>, String>> {
        return tripRepository.getTaggedTrips(tagName)
    }
}