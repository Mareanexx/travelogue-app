package ru.mareanexx.feature_auth.data.profile.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.feature_auth.data.profile.mapper.toEntity
import ru.mareanexx.feature_auth.data.profile.remote.api.UploadProfileApi
import ru.mareanexx.feature_auth.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.feature_auth.domain.profile.UploadProfileRepository
import ru.mareanexx.feature_auth.domain.profile.entity.Profile
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class UploadProfileRepositoryImpl @Inject constructor(
    private val userSession: UserSessionManager,
    private val uploadProfileApi: UploadProfileApi,
    private val profileDao: ProfileDao
): UploadProfileRepository {
    override suspend fun uploadProfile(newProfile: NewProfileRequest, avatar: File?): Flow<BaseResult<Profile, String>> {
        return flow {
            val session = userSession.getSession()
            newProfile.userUUID = session.userUuid

            val jsonBody = Gson().toJson(newProfile).toRequestBody("application/json".toMediaTypeOrNull())

            val avatarPart = avatar?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("avatar", it.name, requestFile)
            }

            val response = uploadProfileApi.uploadProfile(jsonBody, avatarPart)

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
}