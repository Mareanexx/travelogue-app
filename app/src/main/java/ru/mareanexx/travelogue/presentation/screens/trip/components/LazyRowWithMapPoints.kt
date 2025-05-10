package ru.mareanexx.travelogue.presentation.screens.trip.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.MapPointCard
import ru.mareanexx.travelogue.presentation.screens.trip.components.trip.TripDescriptionStartBlock
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.TripViewModel
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form.DateConstraints
import ru.mareanexx.travelogue.presentation.theme.mainBorderColor
import ru.mareanexx.travelogue.presentation.theme.mapPointsRowBack

const val ID_OFFSET = 2
const val LIST_STATE_OFFSET = 1

@Composable
fun LazyRowWithMapPoints(
    profileId: String,
    expandedMapPoint: MutableState<MapPointWithPhotos?>,
    onAddStep: () -> Unit,
    onSetDateConstraints: (DateConstraints) -> Unit,
    tripViewModel: TripViewModel
) {
    val tripData by tripViewModel.tripData.collectAsState()
    val listState = rememberLazyListState()
    val focusedMapPointId = tripViewModel.focusedMapPointId.collectAsState()

    LaunchedEffect(focusedMapPointId.value) {
        val index = tripData!!.mapPoints.indexOfFirst { it.mapPoint.id == focusedMapPointId.value }
        if (index != -1) {
            listState.animateScrollToItem(index + ID_OFFSET)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                val correctedIndex = index - LIST_STATE_OFFSET
                tripData!!.mapPoints.getOrNull(correctedIndex)?.let {
                    tripViewModel.onMapPointFocused(it.mapPoint.id)
                }
            }
    }

    LazyRow(
        state = listState,
        modifier = Modifier.padding(bottom = 25.dp).height(220.dp)
            .shadow(elevation = 10.dp, ambientColor = mainBorderColor.copy(alpha = 0.5f), spotColor = mainBorderColor.copy(alpha = 0.8f))
            .clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)).background(mapPointsRowBack),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { TripDescriptionStartBlock(tripData!!) }
        item { StartFinishIcon(tripData!!.trip.startDate, paddingStart = 25.dp, paddingEnd = 0.dp, R.drawable.home_icon) }
        if (tripData!!.mapPoints.isEmpty()) {
            if (profileId == "me") {
                item {
                    AddFirstStepButton(R.string.add_first_step_btn, onAddFirstStep = {
                        onSetDateConstraints(DateConstraints(lowerBound = tripData!!.trip.startDate, upperBound = tripData!!.trip.endDate))
                        onAddStep()
                    })
                }
            } else {
                item { AddFirstStepButton(R.string.no_map_points_yet, false) {} }
            }
        } else {
            if (profileId == "me") {
                item {
                    AddButtonWithDividers(
                        tripDate = tripData!!, null, nextMapPoint = tripData!!.mapPoints.getOrNull(0),
                        onSetDateConstraints, onAddStep = onAddStep
                    )
                    MapPointCard(
                        tripData!!.trip.startDate, tripData!!.mapPoints[0],
                        onExpandCard = { card -> expandedMapPoint.value = card },
                        onAddNewLike = { tripViewModel.likeMapPoint(it) }, onRemoveLike = { tripViewModel.removeLike(it) }
                    )
                    AddButtonWithDividers(
                        tripDate = tripData!!, previousMapPoint = tripData!!.mapPoints[0], nextMapPoint = tripData!!.mapPoints.getOrNull(1),
                        onSetDateConstraints, onAddStep = onAddStep
                    )
                }
                itemsIndexed(tripData!!.mapPoints.subList(1, tripData!!.mapPoints.size)) { index, mapPointData ->
                    MapPointCard(
                        tripData!!.trip.startDate, mapPointData, onExpandCard = { card -> expandedMapPoint.value = card },
                        onAddNewLike = { tripViewModel.likeMapPoint(it) }, onRemoveLike = { tripViewModel.removeLike(it) }
                    )
                    AddButtonWithDividers(
                        tripDate = tripData!!, previousMapPoint = mapPointData, nextMapPoint = tripData!!.mapPoints.getOrNull(index + 2),
                        onSetDateConstraints, onAddStep = onAddStep
                    )
                }
            } else {
                item {
                    CircleWithDividers()
                    MapPointCard(
                        tripData!!.trip.startDate, tripData!!.mapPoints[0], onExpandCard = { card -> expandedMapPoint.value = card },
                        onAddNewLike = { tripViewModel.likeMapPoint(it) }, onRemoveLike = { tripViewModel.removeLike(it) }
                    )
                    CircleWithDividers()
                }
                items(tripData!!.mapPoints.subList(1, tripData!!.mapPoints.size)) { mapPointData ->
                    MapPointCard(
                        tripData!!.trip.startDate, mapPointData, onExpandCard = { card -> expandedMapPoint.value = card },
                        onAddNewLike = { tripViewModel.likeMapPoint(it) }, onRemoveLike = { tripViewModel.removeLike(it) }
                    )
                    CircleWithDividers()
                }
            }
        }
        item { StartFinishIcon(tripData!!.trip.endDate, paddingStart = 0.dp, paddingEnd = 25.dp, R.drawable.finish_icon) }
    }
}