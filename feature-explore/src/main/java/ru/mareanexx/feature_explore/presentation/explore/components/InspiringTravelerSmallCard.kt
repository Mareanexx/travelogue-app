package ru.mareanexx.feature_explore.presentation.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.components.StartFollowButton
import ru.mareanexx.common.ui.components.UnfollowButton
import ru.mareanexx.feature_explore.domain.entity.InspiringProfile
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.profilePrimaryText
import ru.mareanexx.common.ui.theme.profileSecondaryText
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.core.common.R

@Composable
fun InspiringTravelerSmallCard(
    inspiringProfile: InspiringProfile,
    onUnfollowClicked: () -> Unit,
    onStartFollowClicked: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp).padding(bottom = 15.dp)
            .shadow(elevation = 3.dp, Shapes.small, ambientColor = Color.Black.copy(alpha = 0.3f), spotColor = Color.Black.copy(alpha = 0.2f))
            .background(Color.White, Shapes.small).padding(horizontal = 15.dp, vertical = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onNavigateToOthersProfile(inspiringProfile.id) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                model = "${ApiConfig.apiFilesUrl}${inspiringProfile.avatar}",
                placeholder = painterResource(R.drawable.avatar_placeholder),
                error = painterResource(R.drawable.avatar_placeholder),
                contentDescription = stringResource(R.string.cd_avatar_photo),
                contentScale = ContentScale.Crop
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = inspiringProfile.username,
                    style = MaterialTheme.typography.titleSmall,
                    color = profilePrimaryText, fontSize = 14.sp
                )
                Text(
                    text = inspiringProfile.bio, overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = profileSecondaryText, fontSize = 11.sp
                )
            }
        }
        Spacer(Modifier.weight(1f))
        if (inspiringProfile.isFollowing) {
            UnfollowButton(onUnfollowClicked = onUnfollowClicked)
        } else {
            StartFollowButton(onStartFollowClicked = onStartFollowClicked)
        }
    }
}