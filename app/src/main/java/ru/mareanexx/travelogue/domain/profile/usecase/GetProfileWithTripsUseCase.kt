package ru.mareanexx.travelogue.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import javax.inject.Inject

class GetProfileWithTripsUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(): Flow<BaseResult<ProfileDto, String>> {
        return profileRepository.getProfile()
    }
}