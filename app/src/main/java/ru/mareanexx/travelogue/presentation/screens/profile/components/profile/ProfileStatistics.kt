package ru.mareanexx.travelogue.presentation.screens.profile.components.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.othersprofile.viewmodel.state.FollowingState
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText


@Composable
fun ProfileStatisticsBlock(
    tripsNumber: Int, followersNumber: Int, followingsNumber: Int,
    navigateToFollows: () -> Unit,
    isFollowing: State<FollowingState>? = null
) {
    val resultFollowersNumber = isFollowing?.value?.followersCounter ?: followersNumber

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(end = 60.dp, start = 20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { navigateToFollows() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OneProfileStatsColumn(tripsNumber, R.string.trips)
        OneProfileStatsColumn(
            value = resultFollowersNumber,
            descriptionRes = R.string.followers
        ) { VerticalDivider(color = disabledButtonContainer) }
        OneProfileStatsColumn(followingsNumber, R.string.followings) { VerticalDivider(color = disabledButtonContainer) }
    }
}

@Composable
fun OneProfileStatsColumn(
    value: Int,
    @StringRes descriptionRes: Int,
    prefixDivider: @Composable (() -> Unit)? = null
) {
    Row(horizontalArrangement = Arrangement.spacedBy(15.dp), verticalAlignment = Alignment.CenterVertically) {
        if (prefixDivider != null) prefixDivider()

        Column {
            Text(
                text = "$value",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
                color = profilePrimaryText
            )
            Text(
                text = stringResource(descriptionRes),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal, fontSize = 11.sp),
                color = profileSecondaryText
            )
        }
    }
}