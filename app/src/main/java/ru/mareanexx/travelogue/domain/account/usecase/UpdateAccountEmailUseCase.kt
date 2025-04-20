package ru.mareanexx.travelogue.domain.account.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.account.remote.dto.NewEmailResponse
import ru.mareanexx.travelogue.domain.account.AccountRepository
import ru.mareanexx.travelogue.domain.common.BaseResult
import javax.inject.Inject

class UpdateAccountEmailUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(email: String): Flow<BaseResult<NewEmailResponse, String>> {
        return accountRepository.updateEmail(email)
    }
}