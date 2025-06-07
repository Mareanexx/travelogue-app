package ru.mareanexx.feature_explore.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.components.interactive.CustomPullToRefreshBox
import ru.mareanexx.common.ui.components.interactive.ErrorLoadingContent
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.mapPointsRowBack
import ru.mareanexx.feature_explore.R
import ru.mareanexx.feature_explore.presentation.components.TripCardBigUsername
import ru.mareanexx.feature_explore.presentation.explore.components.TagBackgroundsDB
import ru.mareanexx.feature_explore.presentation.explore.components.skeleton.TaggedTripsSkeleton
import ru.mareanexx.feature_explore.presentation.explore.viewmodel.TagViewModel
import ru.mareanexx.feature_explore.presentation.explore.viewmodel.state.TaggedTripsUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TagScreen(
    tagName: String, imgIndex: Int,
    navigateBack: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    tagViewModel: TagViewModel = hiltViewModel()
) {
    val db = TagBackgroundsDB.repository
    val isRefreshing = tagViewModel.isRefreshing.collectAsState()
    val uiState = tagViewModel.uiState.collectAsState()

    CustomPullToRefreshBox(
        modifier = Modifier.fillMaxSize().background(mapPointsRowBack),
        isRefreshing = isRefreshing.value,
        onRefresh = { tagViewModel.loadTrips() }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier.fillMaxWidth().height(220.dp),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(R.drawable.tag_1),
                        contentDescription = stringResource(db[imgIndex])
                    )
                    Button(
                        modifier = Modifier.padding(start = 15.dp).systemBarsPadding().size(40.dp),
                        shape = Shapes.small,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        onClick = navigateBack
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(ru.mareanexx.core.common.R.drawable.arrow_left),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(ru.mareanexx.core.common.R.string.get_back_cd)
                        )
                    }
                }
            }
            stickyHeader {
                Text(
                    modifier = Modifier.fillMaxWidth().background(Color.White)
                        .padding(top = 15.dp, bottom = 20.dp, start = 15.dp),
                    text = "#$tagName",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            when(uiState.value) {
                TaggedTripsUiState.Loading -> { item { TaggedTripsSkeleton() } }
                TaggedTripsUiState.Error -> {
                    item {
                        ErrorLoadingContent(
                            message = R.string.cant_load_trips_by_tag,
                            onRetry = { tagViewModel.retry() }
                        )
                    }
                }
                is TaggedTripsUiState.Success -> {
                    val tripsData = (uiState.value as TaggedTripsUiState.Success).tripsData
                    items(tripsData) { trip ->
                        TripCardBigUsername(
                            trip = trip,
                            onNavigateToOthersProfile = onNavigateToOthersProfile,
                            onSendReport = { tagViewModel.createReport(trip.id) },
                            onNavigateToTrip = onNavigateToTrip
                        )
                    }
                }
            }
        }
    }
}