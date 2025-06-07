package ru.mareanexx.feature_auth.domain.login.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginRequest
import ru.mareanexx.feature_auth.data.login.remote.dto.LoginResponse
import ru.mareanexx.feature_auth.domain.login.LoginRepository
import ru.mareanexx.feature_auth.domain.login.entity.Login
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest): Flow<BaseResult<Login, WrappedResponse<LoginResponse>>> {
        return loginRepository.login(loginRequest)
    }
}