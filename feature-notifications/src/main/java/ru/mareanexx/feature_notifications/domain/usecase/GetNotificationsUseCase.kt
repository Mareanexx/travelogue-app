package ru.mareanexx.feature_notifications.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
import ru.mareanexx.feature_notifications.domain.NotificationsRepository
import ru.mareanexx.feature_notifications.domain.entity.Notification
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<Notification>, WrappedResponse<String>>> {
        return notificationsRepository.getNotifications()
    }
}