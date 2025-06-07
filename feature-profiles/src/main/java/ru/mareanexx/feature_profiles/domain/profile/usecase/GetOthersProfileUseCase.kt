package ru.mareanexx.feature_profiles.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.domain.profile.ProfileRepository
import ru.mareanexx.feature_profiles.domain.profile.entity.ProfileWithTrips
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetOthersProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(othersId: Int): Flow<BaseResult<ProfileWithTrips, String>> {
        return profileRepository.getOthersProfile(othersId)
    }
}