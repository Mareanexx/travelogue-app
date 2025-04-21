package ru.mareanexx.travelogue.domain.notifications

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.notifications.entity.Notification

interface NotificationsRepository {
    suspend fun getNotifications(): Flow<BaseResult<List<Notification>, WrappedResponse<String>>>
}