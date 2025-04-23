package ru.mareanexx.travelogue.domain.notifications.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.notifications.NotificationsRepository
import javax.inject.Inject

class DeleteAllNotificationsUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<String, WrappedResponse<String>>> {
        return notificationsRepository.deleteAll()
    }
}