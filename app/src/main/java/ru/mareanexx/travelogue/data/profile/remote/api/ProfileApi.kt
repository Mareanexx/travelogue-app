package ru.mareanexx.travelogue.data.profile.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorsProfileResponse
import ru.mareanexx.travelogue.domain.profile.entity.Profile
import java.util.UUID

interface ProfileApi {
    @Multipart
    @POST("profile")
    suspend fun uploadProfile(
        @Part("data") data: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<WrappedResponse<Profile>>

    @GET("profile")
    suspend fun getProfile(@Query("authorUuid") userUuid: UUID): Response<WrappedResponse<AuthorsProfileResponse>>

    @Multipart
    @PATCH("profile")
    suspend fun updateProfile(
        @Part("data") data: RequestBody,
        @Part avatar: MultipartBody.Part?,
        @Part cover: MultipartBody.Part?
    ): Response<WrappedResponse<Profile>>
}