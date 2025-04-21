package ru.mareanexx.travelogue.domain.notifications.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.notifications.NotificationsRepository
import ru.mareanexx.travelogue.domain.notifications.entity.Notification
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<Notification>, WrappedResponse<String>>> {
        return notificationsRepository.getNotifications()
    }
}