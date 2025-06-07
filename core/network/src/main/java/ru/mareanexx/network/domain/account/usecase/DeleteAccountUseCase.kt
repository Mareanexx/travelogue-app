package ru.mareanexx.network.domain.account.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.domain.account.AccountRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(): Flow<BaseResult<String, String>> {
        return accountRepository.deleteAccount()
    }
}