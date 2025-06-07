package ru.mareanexx.feature_profiles.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.profile.remote.dto.ProfileDto
import ru.mareanexx.feature_profiles.domain.profile.ProfileRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetProfileWithTripsUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(): Flow<BaseResult<ProfileDto, String>> {
        return profileRepository.getProfile()
    }
}