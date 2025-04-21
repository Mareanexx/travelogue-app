package ru.mareanexx.travelogue.presentation.screens.notifications

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.notifications.components.NotificationsScreenContent
import ru.mareanexx.travelogue.presentation.screens.notifications.components.skeleton.NotificationsScreenSkeleton
import ru.mareanexx.travelogue.presentation.screens.notifications.viewmodel.NotificationsViewModel
import ru.mareanexx.travelogue.presentation.screens.notifications.viewmodel.event.NotificationsEvent
import ru.mareanexx.travelogue.presentation.screens.notifications.viewmodel.state.NotificationsUiState


@Composable
fun NotificationsScreen(viewModel: NotificationsViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val isRefreshing = viewModel.isRefreshing.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is NotificationsEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).systemBarsPadding()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
    ) {
        Text(text = stringResource(R.string.notifications), style = MaterialTheme.typography.titleMedium)

        when(val state = uiState.value) {
            NotificationsUiState.Loading -> NotificationsScreenSkeleton()
            is NotificationsUiState.Success -> NotificationsScreenContent(
                notifications = state.notifications,
                isRefreshing = isRefreshing.value,
                onRefresh = { viewModel.refresh() }
            )
            is NotificationsUiState.Error -> {} // TODO() реализовать показ диалога
        }
    }
}