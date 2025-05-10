package ru.mareanexx.travelogue.domain.mappoint.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.data.mappoint.remote.dto.NewMapPointRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.mappoint.MapPointRepository
import java.io.File
import javax.inject.Inject

class CreateMapPointUseCase @Inject constructor(
    private val mapPointRepository: MapPointRepository
) {
    suspend operator fun invoke(newMapPointRequest: NewMapPointRequest, photos: List<File>): Flow<BaseResult<MapPointWithPhotos, String>> {
        return mapPointRepository.createNewMapPoint(newMapPointRequest, photos)
    }
}