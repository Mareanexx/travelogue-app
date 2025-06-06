package ru.mareanexx.travelogue.presentation.screens.follows

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.components.CustomPullToRefreshBox
import ru.mareanexx.travelogue.presentation.screens.follows.components.ErrorRetry
import ru.mareanexx.travelogue.presentation.screens.follows.components.FollowsHeader
import ru.mareanexx.travelogue.presentation.screens.follows.components.FollowsSkeleton
import ru.mareanexx.travelogue.presentation.screens.follows.components.OneFollowsCard
import ru.mareanexx.travelogue.presentation.screens.follows.viewmodel.FollowsEvent
import ru.mareanexx.travelogue.presentation.screens.follows.viewmodel.FollowsUiState
import ru.mareanexx.travelogue.presentation.screens.follows.viewmodel.FollowsViewModel
import ru.mareanexx.travelogue.presentation.screens.othersprofile.components.NoTripsPlaceholder

@Composable
fun FollowsScreen(
    username: String,
    profileId: String,
    onNavigateToOthersProfile: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: FollowsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is FollowsEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    when(uiState.value) {
        FollowsUiState.IsLoading -> FollowsSkeleton()
        FollowsUiState.Success -> FollowsLoadedContent(
            profileUsername = username, navigateBack = navigateBack, onNavigateToOthersProfile
        )
        is FollowsUiState.Error -> ErrorRetry(username, navigateBack,
            onRetry = { viewModel.retry() }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FollowsLoadedContent(
    profileUsername: String,
    navigateBack: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    viewModel: FollowsViewModel = hiltViewModel()
) {
    val isRefreshing = viewModel.isRefreshing.collectAsState()
    val followsData = viewModel.followsData.collectAsState()

    val tabIndex = remember { mutableIntStateOf(0) }
    val tabs = listOf(R.string.followers, R.string.followings)

    CustomPullToRefreshBox(
        modifier = Modifier.fillMaxSize().background(Color.White).systemBarsPadding(),
        isRefreshing = isRefreshing.value,
        onRefresh = { viewModel.retry() },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            stickyHeader {
                FollowsHeader(followsData.value, profileUsername, tabIndex, tabs, navigateBack)
            }

            when(tabIndex.intValue) {
                0 -> {
                    if (followsData.value.followers.isEmpty()) {
                        item {
                            NoTripsPlaceholder(
                                image = R.drawable.no_followers_placeholder,
                                title = R.string.no_followers_title,
                                smallText = R.string.no_followers_small_text
                            )
                        }
                    } else {
                        items(followsData.value.followers) { follower ->
                            OneFollowsCard(
                                follow = follower,
                                onStartFollowClicked = { viewModel.followUser(follower) },
                                onUnfollowClicked = { viewModel.unfollowUser(follower) },
                                onNavigateToOthersProfile = onNavigateToOthersProfile,
                            )
                        }
                    }
                }
                1 -> {
                    if (followsData.value.followings.isEmpty()) {
                        item {
                            NoTripsPlaceholder(
                                title = R.string.no_followings_title,
                                smallText = R.string.no_followings_small_text
                            )
                        }
                    } else {
                        items(followsData.value.followings) { following ->
                            OneFollowsCard(
                                follow = following,
                                onStartFollowClicked = { viewModel.followUser(following) },
                                onUnfollowClicked = { viewModel.unfollowUser(following) },
                                onNavigateToOthersProfile = onNavigateToOthersProfile
                            )
                        }
                    }
                }
            }
        }
    }
}