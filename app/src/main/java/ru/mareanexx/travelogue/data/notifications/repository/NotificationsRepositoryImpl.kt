package ru.mareanexx.travelogue.data.notifications.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.notifications.remote.api.NotificationsApi
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.notifications.NotificationsRepository
import ru.mareanexx.travelogue.domain.notifications.entity.Notification
import ru.mareanexx.travelogue.utils.UserSessionManager
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