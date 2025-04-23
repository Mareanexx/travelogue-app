package ru.mareanexx.travelogue.presentation.screens.profile.components.trips

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import ru.mareanexx.travelogue.presentation.screens.profile.components.skeleton.TripsContentSkeleton
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.TripsViewModel
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event.ProfileEvent
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.state.ProfileUiState
import ru.mareanexx.travelogue.presentation.theme.MontserratFamily
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.enabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.primaryText
import java.time.LocalDate


@Composable
fun TripsListMainContent(viewModel: TripsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val tripsData = viewModel.tripsData.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ProfileEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    when (uiState.value) {
        ProfileUiState.Init -> {}
        ProfileUiState.IsLoading -> TripsContentSkeleton()
        ProfileUiState.Showing -> TripsLoadedContent(tripsData)
    }
}

@Composable
fun TripsLoadedContent(tripsData: State<List<Trip>>) {
    LazyColumn(
        contentPadding = PaddingValues(start = 15.dp, end = 15.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(tripsData.value) { trip ->
            TripCard(trip)
        }
    }
}


@Composable
fun TripCard(trip: Trip) {
    Box(modifier = Modifier.fillMaxWidth().height(255.dp).clip(Shapes.medium)) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = "${BuildConfig.API_FILES_URL}${trip.coverPhoto}",
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = stringResource(R.string.cd_trip_cover_photo),
            contentScale = ContentScale.Crop,
        )
        Box(modifier = Modifier.fillMaxSize().padding(vertical = 13.dp, horizontal = 17.dp)) {
            if (trip.status == TripTimeStatus.Current) TripHeaderNowOnATripComponent()
            TripHeaderSettingsComponent(Modifier.align(Alignment.TopEnd))
            BottomTripMainInfo(Modifier.align(Alignment.BottomCenter), trip)
        }
    }
}

@Composable
fun BottomTripMainInfo(modifier: Modifier, trip: Trip) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = trip.name,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TripStatsComponent(date = trip.startDate)
            TripStatsComponent(number = trip.daysNumber, textRes = R.string.days)
            TripStatsComponent(number = 120, textRes = R.string.kilometers)
            TripStatsComponent(number = trip.stepsNumber, textRes = R.string.steps)
            TripVisibilityIcon(trip.type)
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
fun TripStatsComponent(
    number: Int? = null,
    @StringRes textRes: Int? = null,
    date: LocalDate? = null
) {
    Column {
        Text(
            text = if(date == null) "$number" else "${date.year}",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        Text(
            text = date?.month?.name ?: stringResource(textRes!!),
            color = Color.White,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            lineHeight = 11.sp
        )
    }
}

@Composable
fun TripHeaderNowOnATripComponent() {
    val infiniteTransition = rememberInfiniteTransition(label = "colorAnimation")

    val animatedColor by infiniteTransition.animateColor(
        initialValue = enabledButtonContainer,
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorAnimation"
    )

    Row(
        modifier = Modifier.height(20.dp)
            .background(color = Color.Black.copy(alpha = 0.3f), shape = Shapes.large)
            .padding(horizontal = 7.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(7.dp).background(animatedColor, shape = CircleShape))
        Text(
            text = stringResource(R.string.now_on_a_trip),
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            fontFamily = MontserratFamily,
            lineHeight = 10.sp, color = Color.White
        )
    }
}

@Composable
fun TripHeaderSettingsComponent(modifier: Modifier) {
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
                text = { Text(text = stringResource(R.string.edit_trip), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = { TODO("реализовать открытие Modal Bottom редактирования Trip") },
                trailingIcon = { Icon(painterResource(R.drawable.edit_pen_icon), null, tint = primaryText) }
            )
            HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.delete_trip), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = { TODO("реализовать открытие Modal Bottom удаления Trip") },
                trailingIcon = { Icon(painterResource(R.drawable.delete_icon), null, tint = primaryText) }
            )
        }
    }
}