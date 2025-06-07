package ru.mareanexx.feature_explore.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.components.StartFollowButton
import ru.mareanexx.common.ui.components.UnfollowButton
import ru.mareanexx.common.ui.theme.profilePrimaryText
import ru.mareanexx.common.ui.theme.profileSecondaryText
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.core.common.R
import ru.mareanexx.feature_explore.domain.entity.SearchProfile
import ru.mareanexx.feature_explore.domain.entity.SearchTrip

@Composable
fun ProfileSearchItem(
    profile: SearchProfile,
    startFollowUser: () -> Unit,
    unfollowUser: () -> Unit,
    onNavigateToOthersProfile: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onNavigateToOthersProfile
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                model = "${ApiConfig.apiFilesUrl}${profile.avatar}",
                placeholder = painterResource(R.drawable.avatar_placeholder),
                error = painterResource(R.drawable.avatar_placeholder),
                contentDescription = stringResource(R.string.cd_avatar_photo),
                contentScale = ContentScale.Crop
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    profile.username,
                    style = MaterialTheme.typography.titleSmall,
                    color = profilePrimaryText, fontSize = 14.sp
                )
                Text(
                    profile.bio, overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = profileSecondaryText, fontSize = 11.sp
                )
            }
        }
        Spacer(Modifier.weight(1f))
        if (profile.isFollowing) {
            UnfollowButton(onUnfollowClicked = unfollowUser)
        } else {
            StartFollowButton(onStartFollowClicked = startFollowUser)
        }
    }
}

@Composable
fun TripSearchItem(
    trip: SearchTrip,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onNavigateToTrip(trip.id, trip.profileId.toString(), trip.username, trip.avatar ?: "") },
    ) {
        AsyncImage(
            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(4.dp)),
            model = "${ApiConfig.apiFilesUrl}${trip.coverPhoto}",
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = stringResource(R.string.cd_cover_photo),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                trip.name,
                style = MaterialTheme.typography.titleSmall,
                color = profilePrimaryText, fontSize = 14.sp
            )
            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.size(15.dp).clip(CircleShape),
                    model = "${ApiConfig.apiFilesUrl}${trip.avatar}",
                    placeholder = painterResource(R.drawable.avatar_placeholder),
                    error = painterResource(R.drawable.avatar_placeholder),
                    contentDescription = stringResource(R.string.cd_avatar_photo),
                    contentScale = ContentScale.Crop
                )
                Text(
                    trip.username,
                    style = MaterialTheme.typography.bodySmall,
                    color = profileSecondaryText, fontSize = 11.sp
                )
            }
        }
    }
}