package ru.mareanexx.feature_auth.data.register.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_auth.data.register.mapper.mapToEntity
import ru.mareanexx.feature_auth.data.register.remote.api.RegisterApi
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterRequest
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterResponse
import ru.mareanexx.feature_auth.domain.register.RegisterRepository
import ru.mareanexx.feature_auth.domain.register.entity.Register
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val registerApi: RegisterApi
): RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<Register, WrappedResponse<RegisterResponse>>> {
        return flow {
            val response = registerApi.register(registerRequest)
            if (response.isSuccessful) {
                val bodyData = response.body()!!.data
                val registerEntity = bodyData!!.mapToEntity()

                userSessionManager.saveSession(registerEntity.token, registerEntity.userUuid, registerRequest.email)

                emit(BaseResult.Success(registerEntity))
            }
            else {
                emit(BaseResult.Error(WrappedResponse(message = "Register error: ${response.message()}")))
            }
        }
    }
}