package ru.mareanexx.feature_profiles.presentation.screens.others_profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.components.interactive.CustomPullToRefreshBox
import ru.mareanexx.common.ui.components.interactive.ErrorLoadingContent
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.domain.profile.entity.ProfileWithTrips
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.components.NoTripsPlaceholder
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.components.OthersProfileEventHandler
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.components.OthersProfileFollowersAndButtons
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.components.OthersProfileSkeleton
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.OthersProfileViewModel
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.state.OthersProfileUiState
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.profile.ProfileCoverPhoto
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.profile.ProfileHeaderBlock
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.trips.TripCard

@Composable
fun OthersProfileScreen(
    profileId: Int,
    navigateToFollows: (String, String) -> Unit,
    navigateBack: () -> Unit,
    navigateToTrip: (Int, username: String, avatar: String) -> Unit,
    viewModel: OthersProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    OthersProfileEventHandler(viewModel.eventFlow)

    when(uiState.value) {
        OthersProfileUiState.Error -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.Center) {
                ErrorLoadingContent(
                    message = ru.mareanexx.core.common.R.string.error_loading_others_profile,
                    onRetry = { viewModel.retry() }
                )
            }
        }
        OthersProfileUiState.Loading -> { OthersProfileSkeleton() }
        is OthersProfileUiState.Success -> {
            val profileWithTripsData = (uiState.value as OthersProfileUiState.Success).profileWithTrips
            OthersProfileLoadedContent(profileWithTripsData, navigateToFollows, navigateBack, navigateToTrip, viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OthersProfileLoadedContent(
    profileWithTrips: ProfileWithTrips,
    navigateToFollows: (String, String) -> Unit,
    navigateBack: () -> Unit,
    navigateToTrip: (Int, username: String, avatar: String) -> Unit,
    viewModel: OthersProfileViewModel
) {
    val isRefreshing = viewModel.isRefreshing.collectAsState()
    val isFollowing = viewModel.isFollowing.collectAsState()
    val listState = rememberLazyListState()

    val showHeader = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    CustomPullToRefreshBox(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        isRefreshing = isRefreshing.value,
        onRefresh = { viewModel.refresh() }
    ) {
        LazyColumn(
            state = listState, modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Box {
                    ProfileCoverPhoto(profileWithTrips.profile)
                    Button(
                        shape = Shapes.small,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 15.dp, start = 15.dp)
                            .size(40.dp),
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
            stickyHeader { ProfileHeaderBlock(profileWithTrips.profile, showHeader.value) }
            item {
                OthersProfileFollowersAndButtons(
                    profileData = profileWithTrips.profile,
                    isFollowing = isFollowing,
                    navigateToFollows = navigateToFollows,
                    onFollowClicked = { viewModel.followUser(profileWithTrips.profile.id) },
                    onUnfollowClicked = { viewModel.unfollowUser(profileWithTrips.profile.id) },
                    onShowNotImplementedToast = { viewModel.showNotImplementedToast() },
                )
            }
            if (profileWithTrips.trips.isEmpty()) {
                item {
                    NoTripsPlaceholder(
                        image = R.drawable.no_content_placeholder,
                        title = R.string.no_trips_yet,
                        smallText = R.string.no_trips_small_text
                    )
                }
            } else {
                items(profileWithTrips.trips) { trip ->
                    TripCard(
                        whose = "others", trip,
                        navigateToTrip = { navigateToTrip(trip.id, profileWithTrips.profile.username, profileWithTrips.profile.avatar ?: "") },
                        onSendReport = { viewModel.createReport(trip.id) }
                    )
                }
            }
        }
    }
}