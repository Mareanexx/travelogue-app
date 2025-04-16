package ru.mareanexx.travelogue.data.register.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.register.mapper.mapToEntity
import ru.mareanexx.travelogue.data.register.remote.api.RegisterApi
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterRequest
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.register.RegisterRepository
import ru.mareanexx.travelogue.domain.register.entity.RegisterEntity
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerApi: RegisterApi): RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, WrappedResponse<RegisterResponse>>> {
        return flow {
            val response = registerApi.register(registerRequest)
            if (response.isSuccessful) {
                val bodyData = response.body()!!.data
                val registerEntity = bodyData!!.mapToEntity()
                emit(BaseResult.Success(registerEntity))
            }
            else {
                emit(BaseResult.Error(
                    WrappedResponse(
                        message = "Register error: ${response.message()}"
                    ))
                )
            }
        }
    }
}