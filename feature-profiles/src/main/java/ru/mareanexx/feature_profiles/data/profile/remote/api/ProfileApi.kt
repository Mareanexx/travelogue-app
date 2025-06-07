package ru.mareanexx.feature_profiles.data.profile.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Query
import ru.mareanexx.feature_profiles.data.profile.remote.dto.AuthorsProfileResponse
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.feature_profiles.domain.profile.entity.Profile
import ru.mareanexx.feature_profiles.domain.profile.entity.ProfileWithTrips
import ru.mareanexx.network.utils.data.WrappedResponse
import java.util.UUID

interface ProfileApi {
    @GET("profile")
    suspend fun getProfile(@Query("authorUuid") userUuid: UUID): Response<WrappedResponse<AuthorsProfileResponse>>

    @Multipart
    @PATCH("profile")
    suspend fun updateProfile(
        @Part("data") data: RequestBody,
        @Part avatar: MultipartBody.Part?,
        @Part cover: MultipartBody.Part?
    ): Response<WrappedResponse<Profile>>

    @GET("profile/stats")
    suspend fun getUpdatedStats(
        @Query("authorId") authorId: Int
    ): Response<WrappedResponse<UpdatedProfileStatsResponse>>

    @GET("profile/other")
    suspend fun getOthersProfile(
        @Query("othersId") othersId: Int,
        @Query("authorId") authorId: Int
    ) : Response<WrappedResponse<ProfileWithTrips>>
}