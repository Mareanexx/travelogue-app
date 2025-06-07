package ru.mareanexx.feature_profiles.domain.mappoint

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.EditMapPointRequest
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.NewMapPointRequest
import ru.mareanexx.feature_profiles.data.pointphoto.remote.dto.DeletedPhoto
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File

interface MapPointRepository {
    suspend fun createNewMapPoint(newMapPointRequest: NewMapPointRequest, photos: List<File>) : Flow<BaseResult<MapPointWithPhotos, String>>
    suspend fun updateExisted(editMapPoint: EditMapPointRequest, deleted: List<DeletedPhoto>, photos: List<File>) : Flow<BaseResult<MapPointWithPhotos, String>>
    suspend fun delete(mapPointId: Int) : Flow<BaseResult<String, String>>
}