package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import javax.inject.Inject

class GetAuthorsTripsUseCase @Inject constructor(private val tripRepository: TripRepository) {
    suspend operator fun invoke(): Flow<List<Trip>> {
        return tripRepository.getAuthorsTrips()
    }
}