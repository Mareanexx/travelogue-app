package ru.mareanexx.feature_auth.data.login.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_auth.data.login.mapper.mapToEntity
import ru.mareanexx.feature_auth.data.login.remote.api.LoginApi
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginResponse
import ru.mareanexx.feature_auth.domain.login.LoginRepository
import ru.mareanexx.feature_auth.domain.login.entity.Login
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
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
                emit(BaseResult.Error(WrappedResponse(message = "Login error: ${response.message()}")))
            }
        }
    }
}