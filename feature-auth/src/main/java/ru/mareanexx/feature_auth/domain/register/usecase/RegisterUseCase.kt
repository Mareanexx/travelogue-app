package ru.mareanexx.feature_auth.domain.register.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterRequest
import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterResponse
import ru.mareanexx.feature_auth.domain.register.RegisterRepository
import ru.mareanexx.feature_auth.domain.register.entity.Register
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    suspend operator fun invoke(registerRequest: RegisterRequest): Flow<BaseResult<Register, WrappedResponse<RegisterResponse>>> {
        return registerRepository.register(registerRequest)
    }
}