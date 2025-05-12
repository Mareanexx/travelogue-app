package ru.mareanexx.travelogue.presentation.screens.activity

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapState
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.mappoint.mapper.toTrendingTrip
import ru.mareanexx.travelogue.data.mappoint.remote.dto.TrendingTripWithPoints
import ru.mareanexx.travelogue.presentation.components.ErrorLoadingContent
import ru.mareanexx.travelogue.presentation.components.TripCardBigUsername
import ru.mareanexx.travelogue.presentation.screens.activity.components.ActivitySkeleton
import ru.mareanexx.travelogue.presentation.screens.activity.viewmodel.ActivityViewModel
import ru.mareanexx.travelogue.presentation.screens.activity.viewmodel.event.ActivityEvent
import ru.mareanexx.travelogue.presentation.screens.activity.viewmodel.state.ActivityUiState
import ru.mareanexx.travelogue.presentation.screens.trip.components.MapBlockInnerShadow
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText

@Composable
fun ActivityEventHandler(eventFlow: SharedFlow<ActivityEvent>) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is ActivityEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    activityViewModel: ActivityViewModel = hiltViewModel()
) {
    val bitmaps = activityViewModel.mapPointBitmaps.collectAsState()
    val uiState = activityViewModel.uiState.collectAsState()
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )

    ActivityEventHandler(activityViewModel.eventFlow)

    val mapState = rememberMapState(key = "static_map")
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(37.617188, 55.755892))
            zoom(1.0)
            pitch(0.0)
            bearing(0.0)
        }
    }

//    val offset = remember {
//        derivedStateOf {
//            try { val offset = sheetState.bottomSheetState.requireOffset()
//                offset
//            } catch (e: IllegalStateException) {
//                0f
//            }
//        }
//    }

    Box(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.fillMaxWidth().height(650.dp)) {
            StaticMap(mapState, mapViewportState, uiState.value, bitmaps.value, onNavigateToTrip)
            MapBlockInnerShadow(Modifier.align(Alignment.TopCenter))
            Text(
                modifier = Modifier.systemBarsPadding().padding(start = 15.dp),
                text = stringResource(R.string.activity),
                color = Color.White, style = MaterialTheme.typography.titleMedium
            )
        }

        BottomSheetScaffold(
            scaffoldState = sheetState,
            sheetPeekHeight = 200.dp,
            // sheetShape = if (offset.value == 0f) RectangleShape else BottomSheetDefaults.ExpandedShape,
            containerColor = Color.White,
            sheetContainerColor = Color.White,
            sheetContent = {
                Column(modifier = Modifier.fillMaxSize()) {
                    ActivitySheetContent(onNavigateToSearch, onNavigateToTrip, onNavigateToOthersProfile, activityViewModel)
                }
            }
        ) {}
    }
}

@Composable
fun StaticMap(
    mapState: MapState,
    mapViewportState: MapViewportState,
    activityUiState: ActivityUiState,
    bitmaps: Map<Int, Bitmap>,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        mapState = mapState,
        mapViewportState = mapViewportState,
        compass = {}, scaleBar = {}
    ) {
        if (activityUiState is ActivityUiState.Success) {
            activityUiState.tripsData.forEach { trip ->
                val orderedPoints = trip.mapPoints.sortedBy { it.arrivalDate }

                if (orderedPoints.size >= 2) {
                    val polyline = orderedPoints.map { Point.fromLngLat(it.longitude, it.latitude) }
                    PolylineAnnotation(points = polyline) {
                        lineColor = Color.White
                        lineWidth = 4.5
                        lineJoin = LineJoin.ROUND
                    }
                }

                orderedPoints.forEach foreach@ { point ->
                    val bitmap = bitmaps[point.id] ?: return@foreach

                    val icon = rememberIconImage("marker_${point.id}", BitmapPainter(bitmap.asImageBitmap()))

                    PointAnnotation(point = Point.fromLngLat(point.longitude, point.latitude)) {
                        iconImage = icon
                        iconSize = 1.2
                        interactionsState.onClicked {
                            onNavigateToTrip(trip.id, trip.profileId.toString(), trip.username, trip.username)
                            true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivitySheetContent(
    onNavigateToSearch: () -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    activityViewModel: ActivityViewModel
) {
    val context = LocalContext.current
    val uiState = activityViewModel.uiState.collectAsState()

    when(uiState.value) {
        ActivityUiState.Loading -> { ActivitySkeleton() }
        ActivityUiState.Error -> { ErrorLoadingContent(R.string.cant_load_activity, onRetry = { activityViewModel.loadActivity() }) }
        ActivityUiState.NoFollows -> { NoFollowingsMessageBlock(onNavigateToSearch) }
        is ActivityUiState.Success -> {
            val tripsData = (uiState.value as ActivityUiState.Success).tripsData

            LaunchedEffect(Unit) {
                activityViewModel.preloadAllMapPointBitmaps(context, tripsData)
            }

            NowOnATripLazyColumn(
                tripsData, onNavigateToTrip, onNavigateToOthersProfile,
                onCreateReport = { tripId -> activityViewModel.createReport(tripId) }
            )
        }
    }
}

@Composable
fun NowOnATripLazyColumn(
    tripsData: List<TrendingTripWithPoints>,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    onCreateReport: (tripId: Int) -> Unit
) {
    LazyColumn {
        item {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = stringResource(R.string.now_on_a_trip).lowercase().replaceFirstChar { it.uppercaseChar() },
                style = MaterialTheme.typography.titleSmall, color = profilePrimaryText
            )
        }
        items(tripsData) { trip ->
            TripCardBigUsername(
                trip.toTrendingTrip(),
                onNavigateToOthersProfile,
                onSendReport = { onCreateReport(trip.id) },
                onNavigateToTrip
            )
        }
    }
}

@Composable
fun NoFollowingsMessageBlock(onNavigateToSearch: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 70.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_followers_yet), color = profilePrimaryText,
            style = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.Center, lineHeight = 26.sp)
        )
        Button(
            shape = Shapes.medium,
            modifier = Modifier.height(34.dp),
            contentPadding = PaddingValues(horizontal = 15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryText),
            onClick = { onNavigateToSearch() }
        ) {
            Icon(
                modifier = Modifier.size(24.dp).padding(end = 5.dp),
                painter = painterResource(R.drawable.search_icon), tint = Color.White,
                contentDescription = stringResource(R.string.search_follows_cd)
            )
            Text(
                text = stringResource(R.string.search_for_friends_btn), color = Color.White,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewActivityScreen() {
    ActivityScreen({}, { _, _, _, _ -> }, onNavigateToOthersProfile = { _ ->  })
}
