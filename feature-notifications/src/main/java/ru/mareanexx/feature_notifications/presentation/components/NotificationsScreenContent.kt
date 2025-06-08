package ru.mareanexx.feature_notifications.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.components.interactive.CustomPullToRefreshBox
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.ui.theme.profileSecondaryText
import ru.mareanexx.feature_notifications.R
import ru.mareanexx.feature_notifications.domain.entity.Notification

@Composable
fun NotificationsScreenContent(
    isRefreshing: Boolean,
    notifications: List<Notification>,
    onDeleteNotifications: () -> Unit,
    onRefresh: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    CustomPullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        if (notifications.isEmpty()) {
            EmptyNotificationsTexts()
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    NotificationsDropdownMenuAndButton(onDeleteNotifications = onDeleteNotifications)
                }
                items(notifications) { notification ->
                    NotificationCard(
                        notificationData = notification,
                        onNavigateToOthersProfile,
                        onNavigateToTrip
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyNotificationsTexts() {
    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 57.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                text = stringResource(R.string.no_notifications),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge, color = primaryText
            )
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.empty_notifications_list),
                style = MaterialTheme.typography.labelMedium,
                color = profileSecondaryText
            )
        }
    }
}