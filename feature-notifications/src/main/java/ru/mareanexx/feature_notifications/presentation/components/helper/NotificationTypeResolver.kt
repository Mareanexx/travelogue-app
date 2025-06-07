package ru.mareanexx.feature_notifications.presentation.components.helper

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import ru.mareanexx.common.ui.theme.commentNotificationBack
import ru.mareanexx.common.ui.theme.followNotificationBack
import ru.mareanexx.common.ui.theme.likeNotificationBack
import ru.mareanexx.common.ui.theme.mapPointNotificationBack
import ru.mareanexx.common.ui.theme.tripNotificationBack
import ru.mareanexx.feature_notifications.R
import ru.mareanexx.feature_notifications.data.type.NotificationType

data class NotificationTypeResolver(
    val backColor: Color,
    @DrawableRes val icon: Int,
    @StringRes val action: Int,
    @StringRes val actionDirection: Int? = null
)

fun resolveNotificationType(notificationType: NotificationType): NotificationTypeResolver {
    return when(notificationType) {
        NotificationType.Like -> NotificationTypeResolver(likeNotificationBack, ru.mareanexx.core.common.R.drawable.like_notif, R.string.liked_your, R.string.map_point)
        NotificationType.Comment -> NotificationTypeResolver(commentNotificationBack, R.drawable.comment_notif, R.string.commented_on_your, R.string.map_point)
        NotificationType.Follow -> NotificationTypeResolver(followNotificationBack, R.drawable.follow_notif, R.string.followed_you)
        NotificationType.NewTrip -> NotificationTypeResolver(tripNotificationBack, R.drawable.trip_notif, R.string.posted_a_new, R.string.trip)
        NotificationType.NewMapPoint -> NotificationTypeResolver(mapPointNotificationBack, R.drawable.map_point_notif, R.string.added_a_new, R.string.map_point)
    }
}