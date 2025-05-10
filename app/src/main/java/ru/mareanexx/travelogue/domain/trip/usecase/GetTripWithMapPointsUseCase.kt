package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.TripRepository
import javax.inject.Inject

class GetTripWithMapPointsUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(profileId: String, tripId: Int): Flow<BaseResult<TripWithMapPoints, String>> {
        return tripRepository.getTripWithMapPoints(profileId, tripId)
    }
}