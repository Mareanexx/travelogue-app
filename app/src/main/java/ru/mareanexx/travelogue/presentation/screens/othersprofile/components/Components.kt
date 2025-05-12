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
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileButton
import ru.mareanexx.travelogue.presentation.screens.profile.components.profile.ProfileStatisticsBlock
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContent
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun OthersProfileFollowersAndButtons(
    profileData: ProfileDto,
    isFollowing: State<Boolean>,
    navigateToFollows: (String, String) -> Unit,
    onFollowClicked: () -> Unit, onUnfollowClicked: () -> Unit,
    onShowNotImplementedToast: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        ProfileStatisticsBlock(
            tripsNumber = profileData.tripsNumber,
            followersNumber = profileData.followersNumber,
            followingsNumber = profileData.followingNumber,
            navigateToFollows = { navigateToFollows(profileData.username, "${profileData.id}") }
        )

        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            OthersProfileButtonsRow(
                isFollowing,
                onFollowClicked = onFollowClicked,
                onUnfollowClicked = onUnfollowClicked,
                onShowNotImplementedToast = onShowNotImplementedToast
            )
        }
    }
}

@Composable
fun OthersProfileButtonsRow(
    isFollowing: State<Boolean>, onFollowClicked: () -> Unit,
    onUnfollowClicked: () -> Unit,
    onShowNotImplementedToast: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProfileButton(
            text = if (isFollowing.value) R.string.following_btn else R.string.follow_btn,
            containerColor = if (isFollowing.value) disabledButtonContainer else primaryText,
            contentColor = if (isFollowing.value) disabledButtonContent else Color.White,
            horizontalPadding = 55,
            onClick = { if (isFollowing.value) onUnfollowClicked() else onFollowClicked() }
        )

        ProfileButton(
            icon = R.drawable.stats_icon,
            text = R.string.travel_stats,
            horizontalPadding = 34,
            borderColor = Color(0xFFE2E2E2)
        ) { onShowNotImplementedToast() }
        OthersProfileSettingsButton(onShowNotImplementedToast)
    }
}

@Composable
fun OthersProfileSettingsButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier.size(34.dp),
        shape = Shapes.small,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        colors = ButtonDefaults.buttonColors(contentColor = primaryText, containerColor = Color.White),
        border = BorderStroke(width = 2.dp, color = Color(0xFFE2E2E2)),
        onClick = onClick
    ) {
        Icon(painter = painterResource(R.drawable.settings_icon), contentDescription = null)
    }
}