package ru.mareanexx.feature_profiles.domain.mappoint.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.EditMapPointRequest
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.data.pointphoto.remote.dto.DeletedPhoto
import ru.mareanexx.feature_profiles.domain.mappoint.MapPointRepository
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class EditMapPointUseCase @Inject constructor(
    private val mapPointRepository: MapPointRepository
) {
    suspend operator fun invoke(editMapPoint: EditMapPointRequest, deleted: List<DeletedPhoto>, photos: List<File>): Flow<BaseResult<MapPointWithPhotos, String>> {
        return mapPointRepository.updateExisted(editMapPoint, deleted, photos)
    }
}