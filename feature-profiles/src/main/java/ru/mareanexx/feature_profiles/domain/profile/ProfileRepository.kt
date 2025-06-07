package ru.mareanexx.feature_profiles.domain.profile

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_profiles.data.profile.remote.dto.AuthorsProfileResponse
import ru.mareanexx.feature_profiles.data.profile.remote.dto.ProfileDto
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdateProfileRequest
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.feature_profiles.domain.profile.entity.ProfileWithTrips
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File

interface ProfileRepository {
    // get
    suspend fun getProfile(): Flow<BaseResult<ProfileDto, String>>
    suspend fun fetchProfileWithTripsFromNetwork(): Flow<BaseResult<AuthorsProfileResponse, String>>
    suspend fun getUpdatedProfileStats(): Flow<BaseResult<UpdatedProfileStatsResponse, String>>
    suspend fun getOthersProfile(othersId: Int) : Flow<BaseResult<ProfileWithTrips, String>>
    // update
    suspend fun updateProfile(profile: UpdateProfileRequest, avatar: File?, coverPhoto: File?): Flow<BaseResult<ProfileDto, String>>
}