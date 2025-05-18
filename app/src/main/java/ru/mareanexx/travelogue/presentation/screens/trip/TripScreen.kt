package ru.mareanexx.travelogue.presentation.screens.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.presentation.screens.trip.components.LazyRowWithMapPoints
import ru.mareanexx.travelogue.presentation.screens.trip.components.MapBlockInnerShadow
import ru.mareanexx.travelogue.presentation.screens.trip.components.MapBoxWithMapPoints
import ru.mareanexx.travelogue.presentation.screens.trip.components.TripEventHandler
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.ExpandedMapPointCard
import ru.mareanexx.travelogue.presentation.screens.trip.components.trip.TripHeader
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.TripViewModel
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.BottomSheetType
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state.TripUiState
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.mapBoxBackground

@Composable
fun TripScreen(
    profileId: String, tripId: Int,
    username: String, userAvatar: String,
    onNavigateToOthersProfile: (Int) -> Unit,
    navigateBack: () -> Unit,
    tripViewModel: TripViewModel = hiltViewModel()
) {
    val tripData = tripViewModel.tripData.collectAsState()
    val context = LocalContext.current
    val uiState = tripViewModel.uiState.collectAsState()

    LaunchedEffect(tripData.value) {
        tripData.value?.let { tripViewModel.preloadMapPointBitmaps(context) }
    }

    when(uiState.value) {
        TripUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(80.dp).background(color = Color.White.copy(0.3f), shape = Shapes.medium),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(modifier = Modifier.size(60.dp), color = Color.White) }
            }
        }
        TripUiState.Showing -> {
            TripScreenLoadedContent(
                profileId, username, userAvatar,
                tripViewModel, navigateBack, onNavigateToOthersProfile
            )
        }
    }
}

@Composable
fun TripScreenLoadedContent(
    profileId: String,
    username: String, avatar: String,
    tripViewModel: TripViewModel,
    navigateBack: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit
) {
    val mapPointForm = tripViewModel.mapPointForm.collectAsState()
    val tripData = tripViewModel.tripData.collectAsState()
    val mapPointBitmaps = tripViewModel.mapPointBitmaps.collectAsState()
    val focusedMapPointId = tripViewModel.focusedMapPointId.collectAsState()
    val expandedMapPoint = remember { mutableStateOf<MapPointWithPhotos?>(null) }

    TripEventHandler(
        mapPointForm.value,
        tripViewModel.eventFlow, onRetry = { tripViewModel.retry() },
        onClearForm = { tripViewModel.clearForm() },
        onDateSelected = { date -> tripViewModel.onMapPointArrivalDateChanged(date) },
        onTimeSelected = { hour, min -> tripViewModel.onMapPointArrivalTimeChanged(hour, min) },
        onAddStep = { tripViewModel.createMapPoint() }, onEditMapPoint = { tripViewModel.editMapPoint() },
        onUpdateCoordinates = { lat, lng -> tripViewModel.onUpdateStepCoordinates(lat, lng) },
        onNavigateBack = navigateBack
    )

    Box(modifier = Modifier.wrapContentSize()) {
        Column(modifier = Modifier.fillMaxSize().background(mapBoxBackground)) {

            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                MapBoxWithMapPoints(
                    tripData = tripData,
                    mapPointBitmaps = mapPointBitmaps.value,
                    focusedMapPointId = focusedMapPointId,
                    onPointClicked = { tripViewModel.onMapPointFocused(it) }
                )
                MapBlockInnerShadow(Modifier.align(Alignment.TopCenter))
                TripHeader(username, avatar, tripData.value!!,
                    navigateBack = {
                        tripViewModel.setLoadingState()
                        navigateBack()
                    },
                    onNavigateToOthersProfile
                )
            }

            LazyRowWithMapPoints(
                profileId, expandedMapPoint,
                onAddStep = { tripViewModel.showBottomSheet(BottomSheetType.AddStep) },
                onSetDateConstraints = { tripViewModel.setDateConstraints(it) },
                tripViewModel
            )
        }

        if (expandedMapPoint.value != null) {
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                ExpandedMapPointCard(
                    profileId,
                    mapPointData = expandedMapPoint,
                    onDismiss = { expandedMapPoint.value = null },
                    onOpenEditSheet = {
                        tripViewModel.onFillEditForm(expandedMapPoint.value!!)
                        tripViewModel.showBottomSheet(BottomSheetType.EditStep)
                    },
                    onAddLike = { id -> tripViewModel.likeMapPoint(id) }, onRemoveLike = { id -> tripViewModel.removeLike(id) }
                )
            }
        }
    }
}