package ru.mareanexx.network.domain.account.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.data.account.remote.dto.NewEmailResponse
import ru.mareanexx.network.domain.account.AccountRepository
import javax.inject.Inject

class UpdateAccountEmailUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(email: String): Flow<BaseResult<NewEmailResponse, String>> {
        return accountRepository.updateEmail(email)
    }
}