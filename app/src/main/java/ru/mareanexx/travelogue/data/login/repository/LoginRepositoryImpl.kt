package ru.mareanexx.travelogue.data.login.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.login.mapper.mapToEntity
import ru.mareanexx.travelogue.data.login.remote.api.LoginApi
import ru.mareanexx.travelogue.data.login.remote.dto.LoginRequest
import ru.mareanexx.travelogue.data.login.remote.dto.LoginResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.login.LoginRepository
import ru.mareanexx.travelogue.domain.login.entity.Login
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userSession: UserSessionManager,
    private val loginApi: LoginApi
): LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<Login, WrappedResponse<LoginResponse>>> {
        return flow {
            val response = loginApi.login(loginRequest)
            if (response.isSuccessful) {
                val bodyData = response.body()!!.data
                val loginEntity = bodyData!!.mapToEntity()

                userSession.saveSession(loginEntity.token, loginEntity.userUuid, loginRequest.email)

                emit(BaseResult.Success(loginEntity))
            }
            else {
                emit(BaseResult.Error(
                    WrappedResponse(
                        message = "Login error: ${response.message()}")
                    )
                )
            }
        }
    }
}