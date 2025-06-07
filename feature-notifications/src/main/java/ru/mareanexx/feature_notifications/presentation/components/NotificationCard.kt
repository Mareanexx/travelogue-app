package ru.mareanexx.feature_notifications.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.ui.theme.timeText
import ru.mareanexx.common.ui.theme.unreadNotificationIndicator
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.common.utils.TimeAgoResolver
import ru.mareanexx.feature_notifications.R
import ru.mareanexx.feature_notifications.domain.entity.Notification
import ru.mareanexx.feature_notifications.presentation.components.helper.resolveNotificationType


@Composable
fun NotificationCard(notificationData: Notification) {
    val notificationTypeRelatedContent = resolveNotificationType(notificationData.type)
    val timeAgoResolver = TimeAgoResolver

    Row(
        modifier = Modifier.fillMaxWidth()
            .shadow(elevation = 7.dp, shape = Shapes.large, ambientColor = Color.Black.copy(alpha = 0.3f), spotColor = Color.Black.copy(alpha = 0.2f))
            .background(Color.White, Shapes.large)
            .padding(vertical = 10.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.size(50.dp)) {
                AsyncImage(
                    model = "${ApiConfig.apiFilesUrl}${notificationData.avatar}",
                    placeholder = painterResource(ru.mareanexx.core.common.R.drawable.avatar_placeholder),
                    error = painterResource(ru.mareanexx.core.common.R.drawable.avatar_placeholder),
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentDescription = null, contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier.size(18.dp).align(Alignment.BottomEnd)
                        .background(notificationTypeRelatedContent.backColor, CircleShape)
                        .border(width = 1.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(notificationTypeRelatedContent.icon),
                        contentDescription = null, tint = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text = notificationData.username,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = primaryText
                    )
                    Text(
                        text = stringResource(notificationTypeRelatedContent.action),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = primaryText
                    )
                    notificationTypeRelatedContent.actionDirection?.let {
                        Text(
                            text = stringResource(it),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = primaryText
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = timeAgoResolver.resolveTimeAgo(notificationData.createdAt), color = timeText,
                        fontSize = 10.sp, fontWeight = FontWeight.Medium,
                        lineHeight = 10.sp
                    )
                    if (!notificationData.isRead) {
                        Box(modifier = Modifier.background(unreadNotificationIndicator, Shapes.extraSmall).padding(horizontal = 5.dp)) {
                            Text(text = stringResource(R.string.new_indicator).uppercase(), color = Color.White,
                                fontSize = 10.sp, fontWeight = FontWeight.Bold,
                                lineHeight = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}