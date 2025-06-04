package ru.mareanexx.travelogue.presentation.screens.othersprofile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.presentation.screens.othersprofile.viewmodel.state.FollowingState
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileButton
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileStatisticsBlock
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContent
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun OthersProfileFollowersAndButtons(
    profileData: ProfileDto,
    isFollowing: State<FollowingState>,
    navigateToFollows: (String, String) -> Unit,
    onFollowClicked: () -> Unit, onUnfollowClicked: () -> Unit,
    onShowNotImplementedToast: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        ProfileStatisticsBlock(
            isFollowing = isFollowing,
            tripsNumber = profileData.tripsNumber,
            followersNumber = profileData.followersNumber,
            followingsNumber = profileData.followingNumber,
            navigateToFollows = { navigateToFollows(profileData.username, "${profileData.id}") }
        )

        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            OthersProfileButtonsRow(
                isFollowing = isFollowing,
                onFollowClicked = onFollowClicked,
                onUnfollowClicked = onUnfollowClicked,
                onShowNotImplementedToast = onShowNotImplementedToast
            )
        }
    }
}

@Composable
fun OthersProfileButtonsRow(
    isFollowing: State<FollowingState>, onFollowClicked: () -> Unit,
    onUnfollowClicked: () -> Unit,
    onShowNotImplementedToast: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileButton(
            weightModifier = Modifier.weight(2.5f),
            text = if (isFollowing.value.isAuthorFollowing) R.string.following_btn else R.string.follow_btn,
            containerColor = if (isFollowing.value.isAuthorFollowing) disabledButtonContainer else primaryText,
            contentColor = if (isFollowing.value.isAuthorFollowing) disabledButtonContent else Color.White,
            onClick = { if (isFollowing.value.isAuthorFollowing) onUnfollowClicked() else onFollowClicked() }
        )

        ProfileButton(
            weightModifier = Modifier.weight(2.5f),
            icon = R.drawable.stats_icon,
            text = R.string.travel_stats,
            borderColor = Color(0xFFE2E2E2)
        ) { onShowNotImplementedToast() }
        OthersProfileSettingsButton(Modifier.weight(0.5f), onShowNotImplementedToast)
    }
}

@Composable
fun OthersProfileSettingsButton(
    weightModifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = weightModifier.size(34.dp),
        shape = Shapes.small,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        colors = ButtonDefaults.buttonColors(contentColor = primaryText, containerColor = Color.White),
        border = BorderStroke(width = 2.dp, color = Color(0xFFE2E2E2)),
        onClick = onClick
    ) {
        Icon(painter = painterResource(R.drawable.settings_icon), contentDescription = null)
    }
}