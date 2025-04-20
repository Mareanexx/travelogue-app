package ru.mareanexx.travelogue.domain.account.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.account.AccountRepository
import ru.mareanexx.travelogue.domain.common.BaseResult
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(): Flow<BaseResult<String, String>> {
        return accountRepository.deleteAccount()
    }
}