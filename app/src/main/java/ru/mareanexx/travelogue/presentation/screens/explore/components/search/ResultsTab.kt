package ru.mareanexx.travelogue.presentation.screens.explore.components.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.explore.components.skeleton.SearchLoading
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.SearchViewModel
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.state.SearchUiState
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText

@Composable
fun SearchResultsTabs(
    searchUiState: SearchUiState,
    selectedTab: MutableIntState,
    searchViewModel: SearchViewModel,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit
) {
    val searchResults = searchViewModel.searchResults.collectAsState()
    val tabs = listOf(R.string.people_tab, R.string.trips_tab)

    Column {
        TabRow(
            containerColor = Color.White,
            contentColor = primaryText,
            selectedTabIndex = selectedTab.intValue,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.intValue]),
                    color = primaryText
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab.intValue == index,
                    onClick = { selectedTab.intValue = index }
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 15.dp, top = 15.dp),
                        text = stringResource(title),
                        style = MaterialTheme.typography.labelSmall,
                        color = profilePrimaryText
                    )
                }
            }
        }

        when (searchUiState) {
            is SearchUiState.Init -> { InitialSearchPlaceholder() }
            is SearchUiState.Loading -> SearchLoading()
            is SearchUiState.Success -> {
                when (selectedTab.intValue) {
                    0 -> PeopleSearchColumn(
                        searchResults.value.profiles,
                        startFollowUser = { profile -> searchViewModel.followUser(profile) },
                        unfollowUser = { profile -> searchViewModel.unfollowUser(profile) },
                        onNavigateToOthersProfile
                    )
                    1 -> TripSearchColumn(searchResults.value.trips, onNavigateToTrip)
                }
            }
            is SearchUiState.Error -> {
                Text(
                    stringResource(R.string.error_loading_search_results),
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}