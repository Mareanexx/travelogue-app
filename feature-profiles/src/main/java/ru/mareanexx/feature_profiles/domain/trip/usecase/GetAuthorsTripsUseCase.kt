package ru.mareanexx.feature_profiles.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip
import javax.inject.Inject

class GetAuthorsTripsUseCase @Inject constructor(private val tripRepository: TripRepository) {
    suspend operator fun invoke(): Flow<List<Trip>> {
        return tripRepository.getAuthorsTrips()
    }
}