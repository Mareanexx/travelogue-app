package ru.mareanexx.feature_auth.domain.login

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginResponse
import ru.mareanexx.feature_auth.domain.login.entity.Login
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<Login, WrappedResponse<LoginResponse>>>
}