package ru.mareanexx.feature_profiles.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.data.trip.dto.TripStats
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
import javax.inject.Inject

class GetUpdatedTripStats @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(): Flow<List<TripStats>> {
        return tripRepository.getUpdatedTripStats()
    }
}