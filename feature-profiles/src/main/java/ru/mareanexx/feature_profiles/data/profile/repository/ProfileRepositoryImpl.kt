package ru.mareanexx.feature_profiles.data.profile.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_profiles.data.profile.mapper.toDto
import ru.mareanexx.feature_profiles.data.profile.mapper.toEntity
import ru.mareanexx.feature_profiles.data.profile.remote.api.ProfileApi
import ru.mareanexx.feature_profiles.data.profile.remote.dto.AuthorsProfileResponse
import ru.mareanexx.feature_profiles.data.profile.remote.dto.ProfileDto
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdateProfileRequest
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.feature_profiles.domain.profile.ProfileRepository
import ru.mareanexx.feature_profiles.domain.profile.entity.ProfileWithTrips
import ru.mareanexx.network.data.tag.mapper.toEntity
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userSession: UserSessionManager,
    private val profileApi: ProfileApi,
    private val profileDao: ru.mareanexx.data.profile.dao.ProfileDao,
    private val tripDao: ru.mareanexx.data.trip.dao.TripDao,
    private val tagDao: ru.mareanexx.data.tag.dao.TagDao
): ProfileRepository {
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
                            tripDao.insertTrips(result.data.trips.map { it.toEntity() })
                            tagDao.insertTags(
                                result.data.trips.flatMap { trips -> trips.tagList?.map { it.toEntity(trips.id) } ?: emptyList() }
                            )
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