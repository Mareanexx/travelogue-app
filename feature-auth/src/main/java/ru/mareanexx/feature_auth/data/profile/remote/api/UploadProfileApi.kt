package ru.mareanexx.feature_auth.data.profile.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.mareanexx.feature_auth.domain.profile.entity.Profile
import ru.mareanexx.network.utils.data.WrappedResponse

interface UploadProfileApi {
    @Multipart
    @POST("profile")
    suspend fun uploadProfile(
        @Part("data") data: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<WrappedResponse<Profile>>
}