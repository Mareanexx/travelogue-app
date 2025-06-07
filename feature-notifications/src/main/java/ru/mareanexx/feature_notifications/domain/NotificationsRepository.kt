package ru.mareanexx.feature_notifications.domain

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
import ru.mareanexx.feature_notifications.domain.entity.Notification

interface NotificationsRepository {
    suspend fun getNotifications(): Flow<BaseResult<List<Notification>, WrappedResponse<String>>>
    suspend fun deleteAll(): Flow<BaseResult<String, WrappedResponse<String>>>
}