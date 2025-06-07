package ru.mareanexx.feature_explore.presentation.explore.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.components.CardInnerDarkening
import ru.mareanexx.common.ui.components.trip.OthersTripHeaderSettings
import ru.mareanexx.common.ui.components.trip.TripHeaderNowOnATripComponent
import ru.mareanexx.common.ui.components.trip.TripStatsComponent
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.core.common.R
import ru.mareanexx.feature_explore.domain.entity.TrendingTrip

@Composable
fun TrendingTripsRow(
    trendingTrips: List<TrendingTrip>,
    onSendReport: (Int) -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    Text(
        modifier = Modifier.padding(horizontal = 15.dp),
        text = stringResource(ru.mareanexx.feature_explore.R.string.trending_trips_label),
        style = MaterialTheme.typography.labelMedium.copy(fontSize = 20.sp), color = primaryText
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
            model = "${ApiConfig.apiFilesUrl}${trip.coverPhoto}",
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = stringResource(R.string.cd_trip_cover_photo),
            contentScale = ContentScale.Crop,
        )
        CardInnerDarkening(Modifier.align(Alignment.TopCenter))
        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 14.dp).padding(top = 14.dp, bottom = 8.dp)) {
            if (trip.status == ru.mareanexx.data.trip.type.TripTimeStatus.Current)
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
                model = "${ApiConfig.apiFilesUrl}${trip.avatar}",
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