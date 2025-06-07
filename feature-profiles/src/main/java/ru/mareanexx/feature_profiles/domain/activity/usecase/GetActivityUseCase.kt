package ru.mareanexx.feature_profiles.domain.activity.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.ActivityTripWithMapPoints
import ru.mareanexx.feature_profiles.domain.trip.TripRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetActivityUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<ActivityTripWithMapPoints>, String>> {
        return tripRepository.getActivity()
    }
}