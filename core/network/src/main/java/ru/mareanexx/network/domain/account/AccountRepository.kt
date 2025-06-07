package ru.mareanexx.network.domain.account

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.data.account.remote.dto.NewEmailResponse

interface AccountRepository {
    suspend fun updateEmail(newEmail: String): Flow<BaseResult<NewEmailResponse, String>>
    suspend fun deleteAccount(): Flow<BaseResult<String, String>>
    suspend fun getAccount(): Flow<String>
    suspend fun logOut(): Flow<String>
}