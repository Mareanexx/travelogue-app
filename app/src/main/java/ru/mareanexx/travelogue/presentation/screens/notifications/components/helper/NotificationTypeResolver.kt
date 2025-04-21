package ru.mareanexx.travelogue.presentation.screens.notifications.components.helper

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.notifications.type.NotificationType
import ru.mareanexx.travelogue.presentation.theme.commentNotificationBack
import ru.mareanexx.travelogue.presentation.theme.followNotificationBack
import ru.mareanexx.travelogue.presentation.theme.likeNotificationBack
import ru.mareanexx.travelogue.presentation.theme.mapPointNotificationBack
import ru.mareanexx.travelogue.presentation.theme.tripNotificationBack

data class NotificationTypeResolver(
    val backColor: Color,
    @DrawableRes val icon: Int,
    @StringRes val action: Int,
    @StringRes val actionDirection: Int? = null
)

fun resolveNotificationType(notificationType: NotificationType): NotificationTypeResolver {
    return when(notificationType) {
        NotificationType.Like -> NotificationTypeResolver(likeNotificationBack, R.drawable.like_notif, R.string.liked_your, R.string.map_point)
        NotificationType.Comment -> NotificationTypeResolver(commentNotificationBack, R.drawable.comment_notif, R.string.commented_on_your, R.string.map_point)
        NotificationType.Follow -> NotificationTypeResolver(followNotificationBack, R.drawable.follow_notif, R.string.followed_you)
        NotificationType.NewTrip -> NotificationTypeResolver(tripNotificationBack, R.drawable.trip_notif, R.string.posted_a_new, R.string.trip)
        NotificationType.NewMapPoint -> NotificationTypeResolver(mapPointNotificationBack, R.drawable.map_point_notif, R.string.added_a_new, R.string.map_point)
    }
}