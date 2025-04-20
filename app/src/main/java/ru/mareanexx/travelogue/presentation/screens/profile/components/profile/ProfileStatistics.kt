package ru.mareanexx.travelogue.presentation.screens.profile.components.profile

import androidx.annotation.StringRes
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText


@Composable
fun ProfileStatisticsBlock(
    tripsNumber: Int, followersNumber: Int, followingsNumber: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 42.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OneProfileStatsColumn(tripsNumber, R.string.trips)
        OneProfileStatsColumn(followersNumber, R.string.followers) { VerticalDivider() }
        OneProfileStatsColumn(followingsNumber, R.string.followings) { VerticalDivider() }
    }
}

@Composable
fun OneProfileStatsColumn(
    value: Int,
    @StringRes descriptionRes: Int,
    prefixDivider: @Composable (() -> Unit)? = null
) {
    Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        if (prefixDivider != null) prefixDivider()

        Column {
            Text(
                text = "$value",
                style = MaterialTheme.typography.titleSmall,
                color = profilePrimaryText
            )
            Text(
                text = stringResource(descriptionRes),
                style = MaterialTheme.typography.bodySmall,
                color = profileSecondaryText
            )
        }
    }
}