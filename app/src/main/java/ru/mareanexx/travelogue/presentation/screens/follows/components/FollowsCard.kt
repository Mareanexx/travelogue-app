package ru.mareanexx.travelogue.presentation.screens.follows.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.domain.follows.entity.Follows
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText

@Composable
fun OneFollowsCard(
    follow: Follows,
    onUnfollowClicked: () -> Unit, onStartFollowClicked: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onNavigateToOthersProfile(follow.id) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                model = "${BuildConfig.API_FILES_URL}${follow.avatar}",
                placeholder = painterResource(R.drawable.avatar_placeholder),
                error = painterResource(R.drawable.avatar_placeholder),
                contentDescription = stringResource(R.string.cd_avatar_photo),
                contentScale = ContentScale.Crop
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = follow.username,
                    style = MaterialTheme.typography.titleSmall,
                    color = profilePrimaryText, fontSize = 14.sp
                )
                Text(
                    text = follow.bio,
                    style = MaterialTheme.typography.bodySmall,
                    color = profileSecondaryText, fontSize = 11.sp
                )
            }
        }
        if (follow.isFollowing) {
            UnfollowButton(onUnfollowClicked = onUnfollowClicked)
        } else {
            StartFollowButton(onStartFollowClicked = onStartFollowClicked)
        }
    }
}