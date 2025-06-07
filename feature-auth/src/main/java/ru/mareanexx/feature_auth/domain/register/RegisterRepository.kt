package ru.mareanexx.feature_auth.domain.register

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterRequest
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterResponse
import ru.mareanexx.feature_auth.domain.register.entity.Register
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<Register, WrappedResponse<RegisterResponse>>>
}