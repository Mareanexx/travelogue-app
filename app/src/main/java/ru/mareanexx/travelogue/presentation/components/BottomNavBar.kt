package ru.mareanexx.travelogue.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.MontserratFamily
import ru.mareanexx.travelogue.presentation.theme.enabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.unfocusedNavBarItem


enum class FocusedNavItem {
    Profile, Activity, Notifications, Explore
}

@Composable
fun BottomNavBar(
    modifier: Modifier,
    tempItemState: FocusedNavItem,
    navigateToProfile: (() -> Unit)? = null,
    navigateToActivity: (() -> Unit)? = null,
    navigateToNotifications: (() -> Unit)? = null,
    navigateToExplore: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .systemBarsPadding()
            .fillMaxWidth().background(color = Color.White)
            .padding(vertical = 8.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OneNavComponent(tempItemState, FocusedNavItem.Profile, R.drawable.my_profile_icon, R.string.my_profile) {
            navigateToProfile?.invoke()
        }
        OneNavComponent(tempItemState, FocusedNavItem.Activity, R.drawable.group_icon, R.string.activity) {
            navigateToActivity?.invoke()
        }
        OneNavComponent(tempItemState, FocusedNavItem.Notifications, R.drawable.notifs_icon, R.string.notifications) {
            navigateToNotifications?.invoke()
        }
        OneNavComponent(tempItemState, FocusedNavItem.Explore, R.drawable.explore_icon, R.string.explore) {
            navigateToExplore?.invoke()
        }
    }
}

@Composable
fun OneNavComponent(
    tempItemState: FocusedNavItem,
    requiredItemState: FocusedNavItem,
    @DrawableRes icon: Int,
    @StringRes routeName: Int,
    navigateTo: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navigateTo()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(35.dp),
            painter = painterResource(icon), contentDescription = stringResource(routeName),
            tint = if (requiredItemState == tempItemState) enabledButtonContainer else unfocusedNavBarItem
        )
        Text(
            text = stringResource(routeName),
            color = if (requiredItemState == tempItemState) Color.Black else unfocusedNavBarItem,
            fontFamily = MontserratFamily,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar(Modifier, FocusedNavItem.Profile, {}, {}, {}, {})
}