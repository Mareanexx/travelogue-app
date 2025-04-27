package ru.mareanexx.travelogue.presentation.screens.follows

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import ru.mareanexx.travelogue.presentation.screens.follows.components.ErrorRetry
import ru.mareanexx.travelogue.presentation.screens.follows.components.FollowsHeader
import ru.mareanexx.travelogue.presentation.screens.follows.components.FollowsSkeleton
import ru.mareanexx.travelogue.presentation.screens.follows.components.OneFollowsCard
import ru.mareanexx.travelogue.presentation.screens.follows.viewmodel.FollowsEvent
import ru.mareanexx.travelogue.presentation.screens.follows.viewmodel.FollowsUiState
import ru.mareanexx.travelogue.presentation.screens.follows.viewmodel.FollowsViewModel

@Composable
fun FollowsScreen(
    username: String,
    profileId: Int,
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
            profileUsername = username, navigateBack = navigateBack
        )
        is FollowsUiState.Error -> ErrorRetry(username, navigateBack,
            onRetry = { viewModel.retry() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FollowsLoadedContent(
    profileUsername: String,
    navigateBack: () -> Unit,
    viewModel: FollowsViewModel = hiltViewModel()
) {
    val isRefreshing = viewModel.isRefreshing.collectAsState()
    val followsData = viewModel.followsData.collectAsState()

    val tabIndex = remember { mutableIntStateOf(0) }
    val tabs = listOf(R.string.followers, R.string.followings)

    PullToRefreshBox(
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
                    items(followsData.value.followers) { follower ->
                        OneFollowsCard(
                            isFollowing = false,
                            follow = follower,
                            onStartFollowClicked = { viewModel.followUser(follower) },
                            onUnfollowClicked = { viewModel.unfollowUser(follower) }
                        )
                    }
                }
                1 -> {
                    items(followsData.value.followings) { following ->
                        OneFollowsCard(
                            isFollowing = true,
                            follow = following,
                            onStartFollowClicked = { viewModel.followUser(following) },
                            onUnfollowClicked = { viewModel.unfollowUser(following) }
                        )
                    }
                }
            }
        }
    }
}