package ru.mareanexx.feature_profiles.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetTripWithMapPointsUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(profileId: String, tripId: Int): Flow<BaseResult<TripWithMapPoints, String>> {
        return tripRepository.getTripWithMapPoints(profileId, tripId)
    }
}