package ru.mareanexx.feature_profiles.domain.mappoint.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.domain.mappoint.MapPointRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class DeleteMapPointUseCase @Inject constructor(
    private val mapPointRepository: MapPointRepository
) {
    suspend operator fun invoke(mapPointId: Int): Flow<BaseResult<String, String>> {
        return mapPointRepository.delete(mapPointId)
    }
}