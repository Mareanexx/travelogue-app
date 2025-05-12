package ru.mareanexx.travelogue.data.profile.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.profile.mapper.toDto
import ru.mareanexx.travelogue.data.profile.mapper.toEntity
import ru.mareanexx.travelogue.data.profile.remote.api.ProfileApi
import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorsProfileResponse
import ru.mareanexx.travelogue.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.data.profile.remote.dto.UpdateProfileRequest
import ru.mareanexx.travelogue.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.travelogue.data.trip.local.dao.TripDao
import ru.mareanexx.travelogue.data.trip.mapper.toEntity
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.profile.entity.Profile
import ru.mareanexx.travelogue.domain.profile.entity.ProfileWithTrips
import ru.mareanexx.travelogue.utils.UserSessionManager
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userSession: UserSessionManager,
    private val profileApi: ProfileApi,
    private val profileDao: ProfileDao,
    private val tripDao: TripDao
): ProfileRepository {
    override suspend fun uploadProfile(newProfile: NewProfileRequest, avatar: File?): Flow<BaseResult<Profile, String>> {
        return flow {
            val session = userSession.getSession()
            newProfile.userUUID = session.userUuid

            val jsonBody = Gson().toJson(newProfile).toRequestBody("application/json".toMediaTypeOrNull())

            val avatarPart = avatar?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("avatar", it.name, requestFile)
            }

            val response = profileApi.uploadProfile(jsonBody, avatarPart)

            if (response.isSuccessful) {
                val profile = response.body()!!.data!!
                profileDao.insert(profile.toEntity())

                userSession.saveProfileId(profile.id)

                emit(BaseResult.Success(profile))
            } else {
                emit(BaseResult.Error("Error uploading profile: ${response.message()}"))
            }
        }
    }

    override suspend fun getProfile(): Flow<BaseResult<ProfileDto, String>> {
        return flow {
            val profileEntity = profileDao.getProfile()

            if (profileEntity != null) {
                emit(BaseResult.Success(profileEntity.toDto()))
            } else {
                fetchProfileWithTripsFromNetwork().collect { result ->
                    when(result) {
                        is BaseResult.Success -> {
                            profileDao.insert(result.data.profile.toEntity(userSession.getSession().userUuid!!))
                            tripDao.insertTrips(result.data.trips.map { it.toEntity(result.data.profile.id) })
                            userSession.saveProfileId(result.data.profile.id)
                            emit(BaseResult.Success(result.data.profile))
                        }
                        is BaseResult.Error -> {
                            emit(BaseResult.Error(result.error))
                        }
                    }
                }
            }
        }
    }

    override suspend fun fetchProfileWithTripsFromNetwork(): Flow<BaseResult<AuthorsProfileResponse, String>> {
        return flow {
            val session = userSession.getSession()
            val response = profileApi.getProfile(session.userUuid!!)

            if (response.isSuccessful) {
                val fullProfileWithTrips = response.body()!!.data!!
                emit(BaseResult.Success(fullProfileWithTrips))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun updateProfile(
        profile: UpdateProfileRequest,
        avatar: File?, coverPhoto: File?
    ): Flow<BaseResult<ProfileDto, String>> {
        return flow {
            val jsonBody = Gson().toJson(profile).toRequestBody("application/json".toMediaTypeOrNull())

            val avatarPart = avatar?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("avatar", it.name, requestFile)
            }

            val coverPart = coverPhoto?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("cover", it.name, requestFile)
            }

            val response = profileApi.updateProfile(jsonBody, avatarPart, coverPart)

            if (response.isSuccessful) {
                val profileResponse = response.body()!!.data!!
                profileDao.update(profileResponse.toEntity())
                emit(BaseResult.Success(profileResponse.toDto()))
            } else {
                emit(BaseResult.Error("Error editing profile: ${response.body()?.message ?: "Unknown error"}"))
            }
        }
    }

    override suspend fun getUpdatedProfileStats(): Flow<BaseResult<UpdatedProfileStatsResponse, String>> {
        return flow {
            val profileId = userSession.getProfileId()
            val response = profileApi.getUpdatedStats(profileId)

            if (response.isSuccessful) {
                val updatedStats = response.body()!!.data!!
                profileDao.updateStatsOnly(profileId,
                    updatedStats.tripsNumber,
                    updatedStats.followersNumber,
                    updatedStats.followingNumber
                )
                emit(BaseResult.Success(updatedStats))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun getOthersProfile(othersId: Int): Flow<BaseResult<ProfileWithTrips, String>> {
        return flow {
            val authorId = userSession.getProfileId()
            val response = profileApi.getOthersProfile(othersId = othersId, authorId = authorId)
            if (response.isSuccessful) {
                emit(BaseResult.Success(response.body()!!.data!!))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }
}