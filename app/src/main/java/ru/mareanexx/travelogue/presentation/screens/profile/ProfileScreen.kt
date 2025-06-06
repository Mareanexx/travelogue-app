package ru.mareanexx.travelogue.presentation.screens.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.presentation.components.CustomPullToRefreshBox
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileCoverPhoto
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileEventHandler
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileFollowersAndButtons
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileHeaderBlock
import ru.mareanexx.travelogue.presentation.screens.profile.components.skeleton.ProfileContentSkeleton
import ru.mareanexx.travelogue.presentation.screens.profile.components.skeleton.TripsContentSkeleton
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripCard
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripsEventHandler
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.ProfileViewModel
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.TripsViewModel
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event.TripTypifiedDialog
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.state.ProfileUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    navigateToFollows: (String, String) -> Unit,
    navigateToStartScreen: () -> Unit,
    navigateToTrip: (Int, username: String, avatar: String) -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    tripsViewModel: TripsViewModel = hiltViewModel()
) {
    val formState = tripsViewModel.formState.collectAsState()
    val listState = rememberLazyListState()

    TripsEventHandler(
        eventFlow = tripsViewModel.eventFlow,
        onDeleteConfirmed = { tripId -> tripsViewModel.deleteTrip(tripId) },
        enteredTagName = formState.value.newTagName,
        onTagNameChanged = { tripsViewModel.onNewTagNameChanged(it) },
        onAddTagClicked = { tripsViewModel.addNewTag() },
    )

    ProfileEventHandler(
        eventFlow = profileViewModel.eventFlow,
        navigateToStartScreen = navigateToStartScreen,
        onDeleteImageConfirmed = { profileViewModel.onDeleteImageConfirmed(it) }
    )

    val profileUiState = profileViewModel.uiState.collectAsState()
    val profileData = profileViewModel.profileData.collectAsState()
    val tripsData = tripsViewModel.tripsData.collectAsState()
    val tripsUiState = tripsViewModel.uiState.collectAsState()
    val profileIsRefreshing = profileViewModel.isRefreshing.collectAsState()
    val tripIsRefreshing = tripsViewModel.isRefreshing.collectAsState()

    val showHeader = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    var canShowTrips by remember { mutableStateOf(false) }

    CustomPullToRefreshBox(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        isRefreshing = profileIsRefreshing.value && tripIsRefreshing.value,
        onRefresh = {
            profileViewModel.refreshStatistics()
            tripsViewModel.refreshStatistics()
        }
    ) {
        LazyColumn(
            state = listState, modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            when(profileUiState.value) {
                ProfileUiState.IsLoading -> {
                    item { ProfileContentSkeleton() }
                }
                ProfileUiState.Showing -> {
                    canShowTrips = true
                    item { ProfileCoverPhoto(profileData.value) }
                    stickyHeader { ProfileHeaderBlock(profileData.value, showHeader.value) }
                    item {
                        ProfileFollowersAndButtons(
                            profileData,
                            onOpenModalSheet = { type -> profileViewModel.changeBottomSheetType(type, true) },
                            navigateToFollows = navigateToFollows,
                        )
                    }
                }
            }

            if (canShowTrips) {
                when (tripsUiState.value) {
                    ProfileUiState.IsLoading -> {
                        item {
                            LaunchedEffect(Unit) { tripsViewModel.loadTrips() }
                            TripsContentSkeleton()
                        }
                    }
                    ProfileUiState.Showing -> {
                        items(tripsData.value) { trip ->
                            TripCard(
                                trip = trip,
                                navigateToTrip = { navigateToTrip(trip.id, profileData.value!!.username, profileData.value!!.avatar.toString()) },
                                onDeleteTrip = { tripsViewModel.onShowTypifiedDialog(trip.id, TripTypifiedDialog.Delete) },
                                onEditTrip = { tripsViewModel.onEditPanelOpen(trip) }
                            )
                        }
                    }
                }
            }
        }
    }
}