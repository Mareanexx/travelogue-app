package ru.mareanexx.feature_notifications.data.type

import ru.mareanexx.feature_notifications.data.type.NotificationType.Comment
import ru.mareanexx.feature_notifications.data.type.NotificationType.Follow
import ru.mareanexx.feature_notifications.data.type.NotificationType.Like
import ru.mareanexx.feature_notifications.data.type.NotificationType.NewMapPoint
import ru.mareanexx.feature_notifications.data.type.NotificationType.NewTrip

enum class NotificationType {
    Like, Comment, Follow, NewTrip, NewMapPoint
}

fun NotificationType.isNavigateToProfile() =
    when(this) {
        NewTrip, NewMapPoint, Like, Comment -> false
        Follow -> true
    }