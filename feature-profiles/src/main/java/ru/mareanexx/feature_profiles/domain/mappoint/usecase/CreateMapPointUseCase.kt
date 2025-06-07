package ru.mareanexx.feature_profiles.domain.mappoint.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.NewMapPointRequest
import ru.mareanexx.feature_profiles.domain.mappoint.MapPointRepository
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class CreateMapPointUseCase @Inject constructor(
    private val mapPointRepository: MapPointRepository
) {
    suspend operator fun invoke(newMapPointRequest: NewMapPointRequest, photos: List<File>): Flow<BaseResult<MapPointWithPhotos, String>> {
        return mapPointRepository.createNewMapPoint(newMapPointRequest, photos)
    }
}