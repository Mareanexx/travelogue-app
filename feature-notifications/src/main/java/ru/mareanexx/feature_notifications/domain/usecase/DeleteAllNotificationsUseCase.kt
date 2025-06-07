package ru.mareanexx.feature_notifications.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.utils.data.WrappedResponse
import ru.mareanexx.feature_notifications.domain.NotificationsRepository
import javax.inject.Inject

class DeleteAllNotificationsUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<String, WrappedResponse<String>>> {
        return notificationsRepository.deleteAll()
    }
}