package ru.mareanexx.feature_auth.domain.profile

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_auth.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.feature_auth.domain.profile.entity.Profile
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File

interface UploadProfileRepository {
    suspend fun uploadProfile(
        newProfile: NewProfileRequest,
        avatar: File?
    ) : Flow<BaseResult<Profile, String>>
}