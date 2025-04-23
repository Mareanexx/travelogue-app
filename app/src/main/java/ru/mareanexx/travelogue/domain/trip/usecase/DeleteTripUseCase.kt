package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.TripRepository
import javax.inject.Inject

class DeleteTripUseCase @Inject constructor(private val tripRepository: TripRepository) {
    suspend operator fun invoke(tripId: Int): Flow<BaseResult<String, String>> {
        return tripRepository.deleteTrip(tripId)
    }
}