package ru.mareanexx.feature_profiles.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class UpdateTripUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(editTripRequest: EditTripRequest, coverPhoto: File?): Flow<BaseResult<Trip, String>> {
        return tripRepository.updateTrip(editTripRequest, coverPhoto)
    }
}