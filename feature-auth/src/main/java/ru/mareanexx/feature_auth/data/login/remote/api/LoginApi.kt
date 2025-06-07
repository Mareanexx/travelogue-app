package ru.mareanexx.feature_auth.data.login.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginResponse
import ru.mareanexx.network.utils.data.WrappedResponse

interface LoginApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<WrappedResponse<LoginResponse>>
}