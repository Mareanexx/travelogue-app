package ru.mareanexx.feature_notifications.domain.entity

import ru.mareanexx.feature_notifications.data.type.NotificationType
import java.time.OffsetDateTime

data class Notification(
    val id: Int,
    val senderId: Int,
    val avatar: String?,
    val username: String,
    val type: NotificationType,
    val relatedTripId: Int?,
    val relatedPointId: Int?,
    val relatedCommentId: Int?,
    val isRead: Boolean,
    val createdAt: OffsetDateTime
)