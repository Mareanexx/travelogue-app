package ru.mareanexx.travelogue.domain.mappoint.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.mappoint.MapPointRepository
import javax.inject.Inject

class DeleteMapPointUseCase @Inject constructor(
    private val mapPointRepository: MapPointRepository
) {
    suspend operator fun invoke(mapPointId: Int): Flow<BaseResult<String, String>> {
        return mapPointRepository.delete(mapPointId)
    }
}