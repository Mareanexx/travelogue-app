package ru.mareanexx.travelogue.domain.register.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterRequest
import ru.mareanexx.travelogue.data.register.remote.dto.RegisterResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.register.RegisterRepository
import ru.mareanexx.travelogue.domain.register.entity.RegisterEntity
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    suspend operator fun invoke(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, WrappedResponse<RegisterResponse>>> {
        return registerRepository.register(registerRequest)
    }
}