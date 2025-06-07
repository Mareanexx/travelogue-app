package ru.mareanexx.feature_profiles.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class DeleteTripUseCase @Inject constructor(private val tripRepository: TripRepository) {
    suspend operator fun invoke(tripId: Int): Flow<BaseResult<String, String>> {
        return tripRepository.deleteTrip(tripId)
    }
}