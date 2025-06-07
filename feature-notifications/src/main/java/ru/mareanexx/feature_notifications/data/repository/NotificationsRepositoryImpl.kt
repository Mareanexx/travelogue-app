package ru.mareanexx.feature_notifications.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_notifications.data.remote.api.NotificationsApi
import ru.mareanexx.feature_notifications.domain.NotificationsRepository
import ru.mareanexx.feature_notifications.domain.entity.Notification
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
    private val notificationsApi: NotificationsApi,
    private val userSessionManager: UserSessionManager
): NotificationsRepository {
    override suspend fun getNotifications(): Flow<BaseResult<List<Notification>, WrappedResponse<String>>> {
        return flow {
            val profileId = userSessionManager.getProfileId()
            val response = notificationsApi.getAll(profileId)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(WrappedResponse(message = response.body()!!.message)))
            }
        }
    }

    override suspend fun deleteAll(): Flow<BaseResult<String, WrappedResponse<String>>> {
        return flow {
            val profileId = userSessionManager.getProfileId()
            val response = notificationsApi.deleteAll(profileId)
            if (response.isSuccessful) {
                val message = response.body()?.message ?: "Success"
                emit(BaseResult.Success(data = message))
            } else {
                emit(BaseResult.Error(WrappedResponse(message = response.body()?.message ?: "Unknown error")))
            }
        }
    }
}