package ru.mareanexx.travelogue.domain.profile

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorsProfileResponse
import ru.mareanexx.travelogue.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.data.profile.remote.dto.UpdateProfileRequest
import ru.mareanexx.travelogue.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.profile.entity.Profile
import java.io.File

interface ProfileRepository {
    suspend fun uploadProfile(newProfile: NewProfileRequest, avatar: File?): Flow<BaseResult<Profile, String>>
    suspend fun getProfile(): Flow<BaseResult<ProfileDto, String>>
    suspend fun fetchProfileWithTripsFromNetwork(): Flow<BaseResult<AuthorsProfileResponse, String>>
    suspend fun updateProfile(profile: UpdateProfileRequest, avatar: File?, coverPhoto: File?): Flow<BaseResult<ProfileDto, String>>
    suspend fun getUpdatedProfileStats(): Flow<BaseResult<UpdatedProfileStatsResponse, String>>
}