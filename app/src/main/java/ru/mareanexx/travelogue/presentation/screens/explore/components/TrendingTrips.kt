package ru.mareanexx.travelogue.presentation.screens.explore.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.presentation.components.CardInnerDarkening
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripHeaderNowOnATripComponent
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripStatsComponent
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun TrendingTripsRow(
    trendingTrips: List<TrendingTrip>,
    onSendReport: (Int) -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    Text(
        modifier = Modifier.padding(horizontal = 15.dp),
        text = stringResource(R.string.trending_trips_label),
        style = MaterialTheme.typography.labelMedium, color = primaryText
    )

    LazyRow(
        contentPadding = PaddingValues(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(trendingTrips) { trip ->
            TrendingTripCard(trip, onSendReport = { onSendReport(trip.id) }, onNavigateToTrip)
        }
    }
}

@Composable
fun TrendingTripCard(
    trip: TrendingTrip,
    onSendReport: () -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth().size(height = 320.dp, width = 255.dp).clip(Shapes.medium)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onNavigateToTrip(trip.id, trip.profileId.toString(), trip.username, trip.avatar.toString()) }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = "${BuildConfig.API_FILES_URL}${trip.coverPhoto}",
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = stringResource(R.string.cd_trip_cover_photo),
            contentScale = ContentScale.Crop,
        )
        CardInnerDarkening(Modifier.align(Alignment.TopCenter))
        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 14.dp).padding(top = 14.dp, bottom = 8.dp)) {
            if (trip.status == TripTimeStatus.Current)
                TripHeaderNowOnATripComponent()
            OthersTripHeaderSettings(
                Modifier.align(Alignment.TopEnd),
                onSendReport = onSendReport
            )
            TrendingTripMainInfo(Modifier.align(Alignment.BottomCenter), trip)
        }
    }
}

@Composable
fun TrendingTripMainInfo(modifier: Modifier, trip: TrendingTrip) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = trip.name,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(25.dp).clip(CircleShape),
                model = "${BuildConfig.API_FILES_URL}${trip.avatar}",
                placeholder = painterResource(R.drawable.avatar_placeholder),
                error = painterResource(R.drawable.avatar_placeholder),
                contentDescription = stringResource(R.string.cd_avatar_photo),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = "by ${trip.username}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold, color = Color.White
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TripStatsComponent(date = trip.startDate)
            TripStatsComponent(number = trip.daysNumber, textRes = R.string.days)
            TripStatsComponent(number = 120, textRes = R.string.kilometers)
            TripStatsComponent(number = trip.stepsNumber, textRes = R.string.steps)
        }
    }
}

@Composable
fun OthersTripHeaderSettings(modifier: Modifier, onSendReport: () -> Unit) {
    val expandedMenu = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button(
            onClick = { expandedMenu.value = !expandedMenu.value },
            modifier = Modifier.size(height = 20.dp, width = 35.dp),
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.3f), contentColor = Color.White),
            contentPadding = PaddingValues(0.dp)
        ) { Icon(painterResource(R.drawable.more_points_icon), stringResource(R.string.cd_points_settings)) }

        DropdownMenu(
            shape = Shapes.small,
            expanded = expandedMenu.value,
            containerColor = Color.White,
            onDismissRequest = { expandedMenu.value = !expandedMenu.value }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.report_trip), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = onSendReport,
                trailingIcon = { Icon(painterResource(R.drawable.report_icon), null, tint = primaryText) }
            )
        }
    }
}