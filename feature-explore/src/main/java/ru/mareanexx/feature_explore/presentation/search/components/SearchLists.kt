package ru.mareanexx.feature_explore.presentation.search.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.feature_explore.R
import ru.mareanexx.feature_explore.domain.entity.SearchProfile
import ru.mareanexx.feature_explore.domain.entity.SearchTrip

@Composable
fun TripSearchColumn(
    trips: List<SearchTrip>,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    if (trips.isEmpty()) {
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
            text = stringResource(R.string.no_results),
            style = MaterialTheme.typography.labelMedium,
            color = primaryText
        )
    }

    LazyColumn(contentPadding = PaddingValues(horizontal = 15.dp)) {
        items(trips) { trip ->
            TripSearchItem(trip, onNavigateToTrip)
        }
    }
}

@Composable
fun PeopleSearchColumn(
    profiles: List<SearchProfile>,
    startFollowUser: (SearchProfile) -> Unit,
    unfollowUser: (SearchProfile) -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit
) {
    if (profiles.isEmpty()) {
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
            text = stringResource(R.string.no_results),
            style = MaterialTheme.typography.labelMedium,
            color = primaryText
        )
    }
    LazyColumn(contentPadding = PaddingValues(horizontal = 15.dp)) {
        items(profiles) { profile ->
            ProfileSearchItem(
                profile,
                startFollowUser = { startFollowUser(profile) },
                unfollowUser = { unfollowUser(profile) },
                onNavigateToOthersProfile = { onNavigateToOthersProfile(profile.id) }
            )
        }
    }
}