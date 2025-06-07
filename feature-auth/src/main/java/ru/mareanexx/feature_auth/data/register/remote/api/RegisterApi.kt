package ru.mareanexx.feature_auth.data.register.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterRequest
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterResponse
import ru.mareanexx.network.utils.data.WrappedResponse

interface RegisterApi {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<WrappedResponse<RegisterResponse>>
}