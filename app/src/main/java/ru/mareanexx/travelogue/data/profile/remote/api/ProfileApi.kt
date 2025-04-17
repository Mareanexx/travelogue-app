package ru.mareanexx.travelogue.data.profile.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.profile.entity.Profile

interface ProfileApi {
    @Multipart
    @POST("profile")
    suspend fun uploadProfile(
        @Part("data") data: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<WrappedResponse<Profile>>
}