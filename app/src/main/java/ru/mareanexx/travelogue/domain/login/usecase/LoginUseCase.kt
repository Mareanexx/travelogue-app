package ru.mareanexx.travelogue.domain.login.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.login.remote.dto.LoginRequest
import ru.mareanexx.travelogue.data.login.remote.dto.LoginResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.login.LoginRepository
import ru.mareanexx.travelogue.domain.login.entity.Login
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest): Flow<BaseResult<Login, WrappedResponse<LoginResponse>>> {
        return loginRepository.login(loginRequest)
    }
}