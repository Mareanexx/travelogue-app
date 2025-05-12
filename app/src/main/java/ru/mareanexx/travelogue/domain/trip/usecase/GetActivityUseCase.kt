package ru.mareanexx.travelogue.domain.trip.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.mappoint.remote.dto.TrendingTripWithPoints
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.TripRepository
import javax.inject.Inject

class GetActivityUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<TrendingTripWithPoints>, String>> {
        return tripRepository.getActivity()
    }
}