package ru.mareanexx.feature_profiles.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.profile.remote.dto.ProfileDto
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdateProfileRequest
import ru.mareanexx.feature_profiles.domain.profile.ProfileRepository
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(
        updateProfileRequest: UpdateProfileRequest,
        avatar: File?,
        coverPhoto: File?
    ): Flow<BaseResult<ProfileDto, String>> {
        return repository.updateProfile(updateProfileRequest, avatar, coverPhoto)
    }
}