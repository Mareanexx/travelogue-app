package ru.mareanexx.travelogue.data.trip.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.travelogue.data.tag.local.dao.TagDao
import ru.mareanexx.travelogue.data.tag.mapper.toEntity
import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagResponse
import ru.mareanexx.travelogue.data.trip.local.dao.TripDao
import ru.mareanexx.travelogue.data.trip.mapper.toEntity
import ru.mareanexx.travelogue.data.trip.mapper.toTrip
import ru.mareanexx.travelogue.data.trip.remote.api.TripApi
import ru.mareanexx.travelogue.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.travelogue.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.TripRepository
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import ru.mareanexx.travelogue.utils.UserSessionManager
import java.io.File
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val tripApi: TripApi,
    private val tripDao: TripDao,
    private val tagDao: TagDao,
    private val userSessionManager: UserSessionManager
): TripRepository {
    override suspend fun getAuthorsTrips(): Flow<List<Trip>> = flow {
        val tripEntities = tripDao.getTrips()
        val tripIds = tripEntities.map { it.id }

        val tags = tagDao.getTagsForTrips(tripIds)
            .groupBy { it.tripId } // Map<Int, List<TagWithTripId>>

        val trips = tripEntities.map { tripEntity ->
            val tagList = tags[tripEntity.id].orEmpty().map { NewTagResponse(it.id, it.name) }
            tripEntity.toTrip(tagList)
        }

        emit(trips)
    }

    override suspend fun addNewTrip(newTripRequest: NewTripRequest, coverPhoto: File): Flow<BaseResult<Trip, String>> {
        return flow {
            val profileId = userSessionManager.getProfileId()
            newTripRequest.profileId = profileId

            val jsonBody = gson.toJson(newTripRequest).toRequestBody("application/json".toMediaTypeOrNull())
            val coverPart = coverPhoto.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("cover", it.name, requestFile)
            }

            val response = tripApi.addNewTrip(jsonBody, coverPart)
            if (response.isSuccessful) {
                val trip = response.body()!!.data!!
                tripDao.insert(trip.toEntity())
                if (trip.tagList != null) tagDao.insertTags(trip.tagList.map { it.toEntity(trip.id) })
                emit(BaseResult.Success(trip))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun updateTrip(
        editTripRequest: EditTripRequest,
        coverPhoto: File?
    ): Flow<BaseResult<Trip, String>> {
        return flow {
            val jsonBody = gson.toJson(editTripRequest).toRequestBody("application/json".toMediaTypeOrNull())
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
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
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
                emit(BaseResult.Error(response.body() ?: "Unknown error"))
            }
        }
    }
}