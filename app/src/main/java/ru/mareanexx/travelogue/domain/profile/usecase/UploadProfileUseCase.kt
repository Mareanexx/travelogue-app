package ru.mareanexx.travelogue.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.profile.entity.Profile
import java.io.File
import javax.inject.Inject

class UploadProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(
        newProfile: NewProfileRequest,
        avatar: File?
    ): Flow<BaseResult<Profile, String>> {
        return profileRepository.uploadProfile(newProfile, avatar)
    }
}