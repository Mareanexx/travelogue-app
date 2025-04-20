package ru.mareanexx.travelogue.data.trip.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.tag.local.dao.TagDao
import ru.mareanexx.travelogue.data.tag.mapper.toEntity
import ru.mareanexx.travelogue.data.trip.local.dao.TripDao
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity
import ru.mareanexx.travelogue.data.trip.mapper.toEntity
import ru.mareanexx.travelogue.data.trip.remote.api.TripApi
import ru.mareanexx.travelogue.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.travelogue.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import java.io.File
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val tripApi: TripApi,
    private val tripDao: TripDao,
    private val tagDao: TagDao,
    private val profileDao: ProfileDao
): TripRepository {
    override suspend fun getAuthorsTrips(): Flow<List<TripEntity>> {
        return flow {
            val trips = tripDao.getTrips()
            emit(trips)
        }
    }

    override suspend fun addNewTrip(
        newTripRequest: NewTripRequest,
        coverPhoto: File
    ): Flow<BaseResult<Trip, String>> {
        return flow {
            val profileId = profileDao.getProfileId()
            newTripRequest.profileId = profileId

            val jsonBody = Gson().toJson(newTripRequest).toRequestBody("application/json".toMediaTypeOrNull())
            val coverPart = coverPhoto.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("avatar", it.name, requestFile)
            }

            val response = tripApi.addNewTrip(jsonBody, coverPart)
            if (response.isSuccessful) {
                val trip = response.body()!!.data
                tripDao.insert(trip!!.toEntity())
                if (trip.tagList != null) tagDao.insertTags(trip.tagList.map { it.toEntity(trip.id) })
                emit(BaseResult.Success(trip))
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }

    override suspend fun updateTrip(
        editTripRequest: EditTripRequest,
        coverPhoto: File?
    ): Flow<BaseResult<Trip, String>> {
        return flow {
            val jsonBody = Gson().toJson(editTripRequest).toRequestBody("application/json".toMediaTypeOrNull())
            val coverPart = coverPhoto?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("cover", it.name, requestFile)
            }

            val response = tripApi.updateTrip(jsonBody, coverPart)
            if (response.isSuccessful) {
                val trip = response.body()!!.data!!
                tripDao.update(trip.toEntity())
                if (trip.tagList != null) {
                    tagDao.deleteAllByTripId(trip.id)
                    tagDao.insertTags(trip.tagList.map { it.toEntity(trip.id) })
                }
                emit(BaseResult.Success(trip))
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }

    override suspend fun deleteTrip(tripId: Int): Flow<BaseResult<String, String>> {
        return flow {
            val response = tripApi.deleteTrip(tripId)
            if (response.isSuccessful) {
                tripDao.deleteTripById(tripId)

                emit(BaseResult.Success("Successfully deleted trip"))
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }
}