package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.trip.entity.TripStats
import javax.inject.Inject

class GetUpdatedTripStats @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(): Flow<List<TripStats>> {
        return tripRepository.getUpdatedTripStats()
    }
}