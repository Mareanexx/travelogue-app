package ru.mareanexx.travelogue.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.presentation.screens.explore.components.OthersTripHeaderSettings
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripHeaderNowOnATripComponent
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripStatsComponent
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun TripCardBigUsername(
    trip: TrendingTrip,
    onNavigateToOthersProfile: (Int) -> Unit,
    onSendReport: () -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 15.dp).padding(top = 15.dp).fillMaxWidth()) {

        TripHeaderWithUsername(trip, onNavigateToOthersProfile)

        Box(
            modifier = Modifier.fillMaxWidth().height(240.dp).clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onNavigateToTrip(trip.id, trip.profileId.toString(), trip.username, trip.avatar ?: "") }
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
                TripCardMainInfo(Modifier.align(Alignment.BottomCenter), trip)
            }
        }
    }
}

@Composable
fun TripCardMainInfo(alignModifier: Modifier, trip: TrendingTrip) {
    Column(modifier = alignModifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp),
            text = trip.name, color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TripStatsComponent(date = trip.startDate)
            TripStatsComponent(number = trip.daysNumber, textRes = R.string.days)
            TripStatsComponent(number = 0, textRes = R.string.kilometers)
            TripStatsComponent(number = trip.stepsNumber, textRes = R.string.steps)
        }
    }
}

@Composable
fun TripHeaderWithUsername(trip: TrendingTrip, onNavigateToOthersProfile: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .shadow(elevation = 8.dp, spotColor = Color.Black.copy(0.5f), ambientColor = Color.Black.copy(0.7f), shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(Color.White, RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)).padding(15.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onNavigateToOthersProfile(trip.profileId) },
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.clip(CircleShape).size(44.dp),
            model = "${BuildConfig.API_FILES_URL}${trip.avatar}",
            placeholder = painterResource(R.drawable.avatar_placeholder),
            error = painterResource(R.drawable.avatar_placeholder),
            contentDescription = stringResource(R.string.cd_avatar_photo),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = trip.username, color = primaryText,
            style = MaterialTheme.typography.labelMedium
        )
    }
}