package ru.mareanexx.travelogue.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.domain.profile.entity.ProfileWithTrips
import javax.inject.Inject

class GetOthersProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(othersId: Int): Flow<BaseResult<ProfileWithTrips, String>> {
        return profileRepository.getOthersProfile(othersId)
    }
}