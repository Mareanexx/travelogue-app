package ru.mareanexx.feature_profiles.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class AddTripUseCase @Inject constructor(private val tripsRepository: TripRepository) {
    suspend operator fun invoke(newTripRequest: NewTripRequest, coverPhoto: File): Flow<BaseResult<Trip, String>> {
        return tripsRepository.addNewTrip(newTripRequest, coverPhoto)
    }
}