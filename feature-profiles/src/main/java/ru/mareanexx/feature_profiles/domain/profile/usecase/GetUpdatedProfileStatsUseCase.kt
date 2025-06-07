package ru.mareanexx.feature_profiles.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.feature_profiles.domain.profile.ProfileRepository
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetUpdatedProfileStatsUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<UpdatedProfileStatsResponse, String>> {
        return profileRepository.getUpdatedProfileStats()
    }
}