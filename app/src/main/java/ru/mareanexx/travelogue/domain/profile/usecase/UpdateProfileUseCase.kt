package ru.mareanexx.travelogue.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.data.profile.remote.dto.UpdateProfileRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
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