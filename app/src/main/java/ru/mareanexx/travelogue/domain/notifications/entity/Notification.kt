package ru.mareanexx.travelogue.domain.notifications.entity

import ru.mareanexx.travelogue.data.notifications.type.NotificationType
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