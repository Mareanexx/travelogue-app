package ru.mareanexx.travelogue.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileContent
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripsContent

@Composable
fun ProfileScreen(
    navigateToStartScreen: () -> Unit,
    navigateToActivity: () -> Unit,
    navigateToNotifications: () -> Unit,
    navigateToExplore: () -> Unit
) {
    var canShowTrips by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column {
            ProfileContent(navigateToStartScreen, onLoadTrips = { canShowTrips = true })
            if (canShowTrips) { TripsContent() }
        }
    }
}