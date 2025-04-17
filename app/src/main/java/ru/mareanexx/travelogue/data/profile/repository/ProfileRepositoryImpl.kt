package ru.mareanexx.travelogue.data.profile.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.profile.mapper.toEntity
import ru.mareanexx.travelogue.data.profile.remote.api.ProfileApi
import ru.mareanexx.travelogue.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.profile.entity.Profile
import ru.mareanexx.travelogue.utils.UserSessionManager
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userSession: UserSessionManager,
    private val profileApi: ProfileApi,
    private val profileDao: ProfileDao,
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
                emit(BaseResult.Success(profile))
            } else {
                emit(BaseResult.Error("Error uploading profile: ${response.message()}"))
            }
        }
    }
}