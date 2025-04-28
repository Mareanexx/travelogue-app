package ru.mareanexx.travelogue.presentation.screens.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.explore.components.ErrorRetryBlock
import ru.mareanexx.travelogue.presentation.screens.explore.components.InspiringTravelerBigCard
import ru.mareanexx.travelogue.presentation.screens.explore.components.SearchRow
import ru.mareanexx.travelogue.presentation.screens.explore.components.TrendingTagsGrid
import ru.mareanexx.travelogue.presentation.screens.explore.components.TrendingTripsRow
import ru.mareanexx.travelogue.presentation.screens.explore.components.skeleton.ExploreSkeleton
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.ExploreUiState
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.ExploreViewModel
import ru.mareanexx.travelogue.presentation.theme.primaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(viewModel: ExploreViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val isRefreshing = viewModel.isRefreshing.collectAsState()

    when(uiState.value) {
        is ExploreUiState.Error -> { ErrorRetryBlock(onRetry = { viewModel.refresh() }) }
        ExploreUiState.Loading -> { ExploreSkeleton() }
        ExploreUiState.Showing -> {
            PullToRefreshBox(
                isRefreshing = isRefreshing.value,
                onRefresh = { viewModel.refresh() }
            ) {
                ExploreLoadedContent()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreLoadedContent(viewModel: ExploreViewModel = hiltViewModel()) {
    val trendingTrips = viewModel.trendingTrips.collectAsState()
    val trendingTags = viewModel.trendingTags.collectAsState()
    val inspiringTravelers = viewModel.inspiringTravelers.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White).systemBarsPadding()) {
        stickyHeader {
            Text(
                modifier = Modifier.fillMaxWidth().background(Color.White).padding(top = 10.dp, bottom = 15.dp, start = 15.dp, end = 15.dp),
                text = stringResource(R.string.explore),
                style = MaterialTheme.typography.titleMedium
            )
        }

        item { SearchRow() }

        item { TrendingTagsGrid(trendingTags.value) }

        item { TrendingTripsRow(trendingTrips.value) }

        item {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp).padding(bottom = 15.dp),
                text = stringResource(R.string.inspiring_travelers_label),
                style = MaterialTheme.typography.labelMedium, color = primaryText
            )
        }

        items(inspiringTravelers.value) { traveler ->
            InspiringTravelerBigCard(traveler)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true )
@Composable
fun PreviewExploreScreen() {
    ExploreScreen()
}