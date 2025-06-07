package ru.mareanexx.network.data.account.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.utils.DatabaseCleaner
import ru.mareanexx.network.data.account.remote.api.AccountApi
import ru.mareanexx.network.data.account.remote.dto.NewEmailResponse
import ru.mareanexx.network.domain.account.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val accountApi: AccountApi,
    private val databaseCleaner: DatabaseCleaner
): AccountRepository {
    override suspend fun updateEmail(newEmail: String): Flow<BaseResult<NewEmailResponse, String>> {
        return flow {
            val userUuid = userSessionManager.getSession().userUuid!!
            val response = accountApi.updateEmail(userUuid)
            if (response.isSuccessful) {
                val body = response.body()!!.data!!
                userSessionManager.saveNewEmail(body.email)
                emit(BaseResult.Success(body))
            } else {
                emit(BaseResult.Error(response.body()!!.message.toString()))
            }
        }
    }

    override suspend fun deleteAccount(): Flow<BaseResult<String, String>> {
        return flow {
            val userUuid = userSessionManager.getSession().userUuid!!
            val response = accountApi.deleteAccount(userUuid)

            if (response.isSuccessful) {
                userSessionManager.clearSession()
                databaseCleaner.cleanAll()
                emit(BaseResult.Success("Successfully deleted account"))
            } else {
                emit(BaseResult.Error(response.body().toString()))
            }
        }
    }

    override suspend fun getAccount(): Flow<String> {
        return flow { emit(userSessionManager.getUserEmail()) }
    }

    override suspend fun logOut(): Flow<String> {
        return flow {
            userSessionManager.clearSession()
            databaseCleaner.cleanAll()
            emit("All Cleared")
        }
    }
}