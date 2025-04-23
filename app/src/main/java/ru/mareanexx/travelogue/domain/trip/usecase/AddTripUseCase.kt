package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import java.io.File
import javax.inject.Inject

class AddTripUseCase @Inject constructor(private val tripsRepository: TripRepository) {
    suspend operator fun invoke(newTripRequest: NewTripRequest, coverPhoto: File): Flow<BaseResult<Trip, String>> {
        return tripsRepository.addNewTrip(newTripRequest, coverPhoto)
    }
}