package ru.mareanexx.travelogue.presentation.screens.explore

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.explore.components.ErrorRetryBlock
import ru.mareanexx.travelogue.presentation.screens.explore.components.InspiringTravelerBigCard
import ru.mareanexx.travelogue.presentation.screens.explore.components.InspiringTravelerSmallCard
import ru.mareanexx.travelogue.presentation.screens.explore.components.TrendingTagsGrid
import ru.mareanexx.travelogue.presentation.screens.explore.components.TrendingTripsRow
import ru.mareanexx.travelogue.presentation.screens.explore.components.search.Search
import ru.mareanexx.travelogue.presentation.screens.explore.components.search.SearchOverlay
import ru.mareanexx.travelogue.presentation.screens.explore.components.skeleton.ExploreSkeleton
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.ExploreViewModel
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.SearchViewModel
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.event.ExploreEvent
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.state.ExploreUiState
import ru.mareanexx.travelogue.presentation.theme.mapPointsRowBack
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.searchBackground


data class SearchOverlayState(
    val isActive: Boolean = false,
    val query: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    onNavigateToTagScreen: (Int, String) -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    exploreViewModel: ExploreViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val uiState = exploreViewModel.uiState.collectAsState()
    val isRefreshing = exploreViewModel.isRefreshing.collectAsState()
    val searchOverlayState = searchViewModel.searchOverlayState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState.value) {
            is ExploreUiState.Error -> { ErrorRetryBlock(onRetry = { exploreViewModel.refresh() }) }
            ExploreUiState.Loading -> { ExploreSkeleton() }
            ExploreUiState.Showing -> {
                PullToRefreshBox(
                    isRefreshing = isRefreshing.value,
                    onRefresh = { exploreViewModel.refresh() }
                ) {
                    ExploreLoadedContent(
                        exploreViewModel,
                        onSearchBarClick = { searchViewModel.onToggleSearchOverlay(true) },
                        onNavigateToTagScreen, onNavigateToTrip, onNavigateToOthersProfile
                    )
                }
            }
        }

        if (searchOverlayState.value.isActive) {
            SearchOverlay(
                state = searchOverlayState.value,
                onQueryChanged = { query -> searchViewModel.onQueryChanged(query) },
                onClearQuery = { searchViewModel.clearQuery() },
                onClose = { searchViewModel.onToggleSearchOverlay(false); searchViewModel.clearQuery() },
                onNavigateToOthersProfile = onNavigateToOthersProfile,
                onNavigateToTrip = onNavigateToTrip,
                searchViewModel = searchViewModel
            )
        }
    }
}

@Composable
fun ExploreEventHandler(eventFlow: SharedFlow<ExploreEvent>) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is ExploreEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreLoadedContent(
    viewModel: ExploreViewModel,
    onSearchBarClick: () -> Unit,
    onNavigateToTagScreen: (Int, String) -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit
) {
    val trendingTrips = viewModel.trendingTrips.collectAsState()
    val trendingTags = viewModel.trendingTags.collectAsState()
    val inspiringTravelers = viewModel.inspiringTravelers.collectAsState()

    ExploreEventHandler(viewModel.eventFlow)

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(mapPointsRowBack)
            .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
    ) {
        stickyHeader {
            Text(
                modifier = Modifier.fillMaxWidth().background(mapPointsRowBack).padding(top = 10.dp, bottom = 15.dp, start = 15.dp, end = 15.dp),
                text = stringResource(R.string.explore),
                style = MaterialTheme.typography.titleMedium
            )
        }

        item { Search(onSearchBarClick) }

        item { TrendingTagsGrid(trendingTags.value, onNavigateToTagScreen = onNavigateToTagScreen) }

        item {
            Column(modifier = Modifier.background(Color.White)) {
                HorizontalDivider(modifier = Modifier.padding(bottom = 30.dp), color = searchBackground, thickness = 1.dp)
                TrendingTripsRow(trendingTrips.value,
                    onSendReport = { tripId -> viewModel.createReport(tripId) },
                    onNavigateToTrip
                )
                HorizontalDivider(color = searchBackground, thickness = 1.dp)
            }
        }

        item {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp).padding(bottom = 15.dp, top = 25.dp),
                text = stringResource(R.string.inspiring_travelers_label),
                style = MaterialTheme.typography.labelMedium, color = primaryText
            )
        }

        items(inspiringTravelers.value.take(3)) { traveler ->
            InspiringTravelerBigCard(traveler,
                onStartFollowClick = { viewModel.followUser(traveler) },
                onUnfollowClick = { viewModel.unfollowUser(traveler) },
                onNavigateToOthersProfile
            )
        }
        if (inspiringTravelers.value.size > 3) {
            items(inspiringTravelers.value.subList(3, inspiringTravelers.value.size)) { traveler ->
                InspiringTravelerSmallCard(traveler,
                    onUnfollowClicked = { viewModel.unfollowUser(traveler) },
                    onStartFollowClicked = { viewModel.followUser(traveler) },
                    onNavigateToOthersProfile
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true )
@Composable
fun PreviewExploreScreen() {
    ExploreScreen(onNavigateToTagScreen = ({ _, _ -> }), onNavigateToTrip = { _, _, _, _ -> }, onNavigateToOthersProfile = { _ -> })
}