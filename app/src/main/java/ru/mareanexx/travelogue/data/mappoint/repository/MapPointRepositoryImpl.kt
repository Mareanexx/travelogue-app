package ru.mareanexx.travelogue.data.mappoint.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.travelogue.data.mappoint.local.dao.MapPointDao
import ru.mareanexx.travelogue.data.mappoint.mapper.toEntity
import ru.mareanexx.travelogue.data.mappoint.remote.api.MapPointApi
import ru.mareanexx.travelogue.data.mappoint.remote.dto.EditMapPointRequest
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.data.mappoint.remote.dto.NewMapPointRequest
import ru.mareanexx.travelogue.data.pointphoto.local.dao.PointPhotoDao
import ru.mareanexx.travelogue.data.pointphoto.mapper.toEntity
import ru.mareanexx.travelogue.data.pointphoto.remote.dto.DeletedPhoto
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.mappoint.MapPointRepository
import java.io.File
import javax.inject.Inject

class MapPointRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val mapPointDao: MapPointDao,
    private val pointPhotoDao: PointPhotoDao,
    private val mapPointApi: MapPointApi
) : MapPointRepository {

    override suspend fun createNewMapPoint(newMapPointRequest: NewMapPointRequest, photos: List<File>): Flow<BaseResult<MapPointWithPhotos, String>> {
        return flow {
            val requestBody = gson.toJson(newMapPointRequest).toRequestBody("application/json".toMediaTypeOrNull())
            val photoParts = preparePhotoParts(photos)

            val response = mapPointApi.addNew(requestBody, photoParts)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                mapPointDao.insert(data.mapPoint.toEntity())
                pointPhotoDao.insertAll(data.photos.map { it.toEntity() })
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun updateExisted(
        editMapPoint: EditMapPointRequest,
        deleted: List<DeletedPhoto>,
        photos: List<File>
    ): Flow<BaseResult<MapPointWithPhotos, String>> {
        return flow {
            val dataRequestBody = gson.toJson(editMapPoint).toRequestBody("application/json".toMediaTypeOrNull())
            val deletedRequestBody = gson.toJson(deleted).toRequestBody("application/json".toMediaTypeOrNull())
            val photoParts = preparePhotoParts(photos)

            val response = mapPointApi.update(data = dataRequestBody, deleted = deletedRequestBody, photos = photoParts)

            if (response.isSuccessful) {
                val updated = response.body()!!.data!!
                mapPointDao.update(updated.mapPoint.toEntity())
                pointPhotoDao.deleteByMapPointId(updated.mapPoint.id)
                pointPhotoDao.insertAll(updated.photos.map { it.toEntity() })
                emit(BaseResult.Success(updated))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun delete(mapPointId: Int): Flow<BaseResult<String, String>> {
        return flow {
            val response = mapPointApi.delete(mapPointId)
            if (response.isSuccessful) {
                mapPointDao.deleteById(mapPointId)
                pointPhotoDao.deleteByMapPointId(mapPointId)
                emit(BaseResult.Success(response.body()!!.data!!))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    private fun preparePhotoParts(photoFiles: List<File>): List<MultipartBody.Part> {
        return photoFiles.mapIndexed { _, file ->
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("photos", file.name, requestFile)
        }
    }
}