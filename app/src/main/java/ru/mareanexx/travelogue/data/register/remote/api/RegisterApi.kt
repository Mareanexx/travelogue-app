package ru.mareanexx.travelogue.data.register.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterRequest
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterResponse

interface RegisterApi {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<WrappedResponse<RegisterResponse>>
}