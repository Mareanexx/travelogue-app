package ru.mareanexx.feature_notifications.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.components.interactive.AreYouSureDialog
import ru.mareanexx.common.ui.components.interactive.ErrorLoadingContent
import ru.mareanexx.common.ui.theme.mapPointsRowBack
import ru.mareanexx.feature_notifications.R
import ru.mareanexx.feature_notifications.presentation.components.NotificationsScreenContent
import ru.mareanexx.feature_notifications.presentation.components.skeleton.NotificationsScreenSkeleton
import ru.mareanexx.feature_notifications.presentation.viewmodel.NotificationsViewModel
import ru.mareanexx.feature_notifications.presentation.viewmodel.event.NotificationsEvent
import ru.mareanexx.feature_notifications.presentation.viewmodel.state.NotificationsUiState


@Composable
fun NotificationsScreen(viewModel: NotificationsViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val isRefreshing = viewModel.isRefreshing.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is NotificationsEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                NotificationsEvent.ShowDeleteDialog -> { showDialog = true }
            }
        }
    }

    if (showDialog) { AreYouSureDialog(
        additional = R.string.all_notifications_variant_del,
        onCancelClicked = { showDialog = false },
        onDeleteClicked = {
            viewModel.deleteAll()
            showDialog = false
        },
    )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = mapPointsRowBack)
        .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(ru.mareanexx.core.common.R.string.notifications),
            style = MaterialTheme.typography.titleMedium
        )

        when(val state = uiState.value) {
            NotificationsUiState.Loading -> NotificationsScreenSkeleton()
            is NotificationsUiState.Success -> NotificationsScreenContent(
                notifications = state.notifications,
                isRefreshing = isRefreshing.value,
                onDeleteNotifications = { viewModel.onDeleteVariantClicked() },
                onRefresh = { viewModel.refresh() }
            )
            is NotificationsUiState.Error -> {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp)) {
                    ErrorLoadingContent(
                        message = ru.mareanexx.core.common.R.string.retry_description,
                        onRetry = { viewModel.refresh() }
                    )
                }
            }
        }
    }
}