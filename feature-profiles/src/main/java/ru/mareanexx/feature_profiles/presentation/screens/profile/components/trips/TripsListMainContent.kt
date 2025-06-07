package ru.mareanexx.feature_profiles.presentation.screens.profile.components.trips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import ru.mareanexx.common.ui.components.CardInnerDarkening
import ru.mareanexx.common.ui.components.trip.OthersTripHeaderSettings
import ru.mareanexx.common.ui.components.trip.TripHeaderNowOnATripComponent
import ru.mareanexx.common.ui.components.trip.TripStatsComponent
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.core.common.R
import ru.mareanexx.data.trip.type.TripTimeStatus
import ru.mareanexx.data.trip.type.TripVisibilityType
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip

@Composable
fun TripCard(
    whose: String = "me", trip: Trip, navigateToTrip: () -> Unit,
    onDeleteTrip: (() -> Unit)? = null, onEditTrip: (() -> Unit)? = null,
    onSendReport: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(255.dp)
            .padding(horizontal = 15.dp).clip(Shapes.medium)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { navigateToTrip() }
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
        Box(modifier = Modifier.fillMaxSize().padding(vertical = 13.dp, horizontal = 17.dp)) {
            if (trip.status == TripTimeStatus.Current)
                TripHeaderNowOnATripComponent()
            if (whose == "me") {
                TripHeaderSettingsComponent(Modifier.align(Alignment.TopEnd), onEditTrip = onEditTrip ?: {}, onDeleteTrip = onDeleteTrip ?: {})
            } else {
                OthersTripHeaderSettings(Modifier.align(Alignment.TopEnd), onSendReport = onSendReport ?: {})
            }
            BottomTripMainInfo(whose, Modifier.align(Alignment.BottomCenter), trip)
        }
    }
}

@Composable
fun BottomTripMainInfo(whose: String, modifier: Modifier, trip: Trip) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = trip.name,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TripStatsComponent(date = trip.startDate)
            TripStatsComponent(number = trip.daysNumber, textRes = R.string.days)
            TripStatsComponent(number = 120, textRes = R.string.kilometers)
            TripStatsComponent(number = trip.stepsNumber, textRes = R.string.steps)
            if (whose == "me") TripVisibilityIcon(trip.type)
        }
    }
}

@Composable
fun TripVisibilityIcon(visibility: TripVisibilityType) {
    Icon(
        painter = painterResource(if(visibility == TripVisibilityType.Private) R.drawable.lock_icon else R.drawable.public_icon),
        contentDescription = stringResource(R.string.cd_trip_visibility),
        tint = Color.White
    )
}

@Composable
fun TripHeaderSettingsComponent(
    modifier: Modifier,
    onEditTrip: () -> Unit,
    onDeleteTrip: () -> Unit
) {
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
                text = { Text(text = stringResource(ru.mareanexx.feature_profiles.R.string.edit_trip), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = { onEditTrip() },
                trailingIcon = { Icon(painterResource(R.drawable.edit_pen_icon), null, tint = primaryText) }
            )
            HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
            DropdownMenuItem(
                text = { Text(text = stringResource(ru.mareanexx.feature_profiles.R.string.delete_trip), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = { onDeleteTrip() },
                trailingIcon = { Icon(painterResource(R.drawable.delete_icon), null, tint = primaryText) }
            )
        }
    }
}