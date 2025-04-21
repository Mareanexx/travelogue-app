package ru.mareanexx.travelogue.presentation.screens.notifications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.domain.notifications.entity.Notification
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreenContent(
    isRefreshing: Boolean,
    notifications: List<Notification>,
    onRefresh: () -> Unit
) {
    val pullState = rememberPullToRefreshState()

    if (notifications.isEmpty()) {
        EmptyNotificationsTexts()
        return
    }

    NotificationsDropdownMenuAndButton()

    PullToRefreshBox(
        state = pullState,
        modifier = Modifier.fillMaxWidth(),
        isRefreshing = isRefreshing,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter), isRefreshing = isRefreshing,
                containerColor = Color.White, color = primaryText, state = pullState
            )
        },
        onRefresh = { onRefresh() }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(notification)
            }
        }
    }
}

@Composable
fun EmptyNotificationsTexts() {
    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 57.dp)) {
        Column(modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
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