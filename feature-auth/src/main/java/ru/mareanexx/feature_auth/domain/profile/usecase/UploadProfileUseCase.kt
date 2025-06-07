package ru.mareanexx.feature_auth.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_auth.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.feature_auth.domain.profile.UploadProfileRepository
import ru.mareanexx.feature_auth.domain.profile.entity.Profile
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class UploadProfileUseCase @Inject constructor(
    private val uploadProfileRepository: UploadProfileRepository
) {
    suspend operator fun invoke(
        newProfile: NewProfileRequest,
        avatar: File?
    ): Flow<BaseResult<Profile, String>> {
        return uploadProfileRepository.uploadProfile(newProfile, avatar)
    }
}