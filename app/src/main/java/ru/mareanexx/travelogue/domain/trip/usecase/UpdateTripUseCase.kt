package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import java.io.File
import javax.inject.Inject

class UpdateTripUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(editTripRequest: EditTripRequest, coverPhoto: File?): Flow<BaseResult<Trip, String>> {
        return tripRepository.updateTrip(editTripRequest, coverPhoto)
    }
}