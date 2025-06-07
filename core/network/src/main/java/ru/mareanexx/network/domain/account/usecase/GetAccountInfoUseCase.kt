package ru.mareanexx.network.domain.account.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.domain.account.AccountRepository
import javax.inject.Inject

class GetAccountInfoUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(): Flow<String> {
        return accountRepository.getAccount()
    }
}