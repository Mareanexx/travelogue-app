package ru.mareanexx.travelogue.domain.mappoint.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.mappoint.remote.dto.EditMapPointRequest
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.data.pointphoto.remote.dto.DeletedPhoto
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.mappoint.MapPointRepository
import java.io.File
import javax.inject.Inject

class EditMapPointUseCase @Inject constructor(
    private val mapPointRepository: MapPointRepository
) {
    suspend operator fun invoke(editMapPoint: EditMapPointRequest, deleted: List<DeletedPhoto>, photos: List<File>): Flow<BaseResult<MapPointWithPhotos, String>> {
        return mapPointRepository.updateExisted(editMapPoint, deleted, photos)
    }
}