package ru.mareanexx.travelogue.domain.register

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterRequest
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.register.entity.Register

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<Register, WrappedResponse<RegisterResponse>>>
}