package ru.mareanexx.travelogue.domain.account

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.account.remote.dto.NewEmailResponse
import ru.mareanexx.travelogue.domain.common.BaseResult

interface AccountRepository {
    suspend fun updateEmail(newEmail: String): Flow<BaseResult<NewEmailResponse, String>>
    suspend fun deleteAccount(): Flow<BaseResult<String, String>>
    suspend fun getAccount(): Flow<String>
    suspend fun logOut(): Flow<String>
}