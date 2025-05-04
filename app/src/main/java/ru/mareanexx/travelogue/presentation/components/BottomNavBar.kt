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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.MontserratFamily
import ru.mareanexx.travelogue.presentation.theme.enabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.unfocusedNavBarItem

@Composable
fun BottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(bottom = 20.dp).fillMaxWidth().background(Color.White)
            .padding(vertical = 8.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavItem("profile", R.drawable.my_profile_icon, R.string.my_profile, selectedTab == "profile") {
            onTabSelected("profile")
        }
        NavItem("activity", R.drawable.group_icon, R.string.activity, selectedTab == "activity") {
            onTabSelected("activity")
        }
        NavItem("notifications", R.drawable.notifs_icon, R.string.notifications, selectedTab == "notifications") {
            onTabSelected("notifications")
        }
        NavItem("explore", R.drawable.explore_icon, R.string.explore, selectedTab == "explore") {
            onTabSelected("explore")
        }
    }
}

@Composable
fun NavItem(
    route: String,
    @DrawableRes icon: Int,
    @StringRes label: Int,
    selected: Boolean,
    navigateTo: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { navigateTo() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(35.dp),
            painter = painterResource(icon),
            contentDescription = stringResource(label),
            tint = if (selected) enabledButtonContainer else unfocusedNavBarItem
        )
        Text(
            text = stringResource(label),
            color = if (selected) Color.Black else unfocusedNavBarItem,
            fontFamily = MontserratFamily,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}