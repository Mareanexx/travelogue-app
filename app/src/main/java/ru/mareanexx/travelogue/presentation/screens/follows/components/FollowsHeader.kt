package ru.mareanexx.travelogue.presentation.screens.follows.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText


@Composable
fun FollowsHeader(
    followsData: FollowersAndFollowingsResponse,
    profileUsername: String,
    tabIndex: MutableIntState,
    tabs: List<Int>,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().shadow(5.dp).background(Color.White).padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { navigateBack() },
                painter = painterResource(R.drawable.arrow_back_icon),
                contentDescription = stringResource(R.string.get_back_cd)
            )
            Text(
                text = profileUsername,
                style = MaterialTheme.typography.titleSmall,
                color = profilePrimaryText
            )
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = stringResource(R.string.search_follows_cd)
            )
        }

        TabRow(
            containerColor = Color.White,
            contentColor = primaryText,
            selectedTabIndex = tabIndex.intValue,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex.intValue]),
                    color = primaryText
                )
            }
        ) {
            tabs.forEachIndexed { curIndex, title ->
                FollowsTab(
                    value = if (curIndex == 0) followsData.followers.size else followsData.followings.size,
                    title, tabIndex, curIndex
                )
            }
        }
    }
}

@Composable
fun FollowsTab(
    value: Int,
    @StringRes tabTitle: Int,
    tabIndex: MutableIntState,
    curIndex: Int
) {
    Tab(
        selected = tabIndex.intValue == curIndex,
        onClick = { tabIndex.intValue = curIndex }
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp, top = 10.dp),
            text = "$value ${stringResource(tabTitle)}",
            style = MaterialTheme.typography.labelSmall,
            color = profilePrimaryText
        )
    }
}