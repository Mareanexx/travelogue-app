package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity
import ru.mareanexx.travelogue.domain.trip.TripRepository
import javax.inject.Inject

class GetAuthorsTripsUseCase @Inject constructor(private val tripRepository: TripRepository) {
    suspend operator fun invoke(): Flow<List<TripEntity>> {
        return tripRepository.getAuthorsTrips()
    }
}