package ru.mareanexx.travelogue.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(bottom = 20.dp)
            .padding(vertical = 8.dp, horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavItem(R.drawable.my_profile_icon, R.string.my_profile, selectedTab == "profile") {
            onTabSelected("profile")
        }
        NavItem(R.drawable.group_icon, R.string.activity, selectedTab == "activity") {
            onTabSelected("activity")
        }
        NavItem(R.drawable.notifs_icon, R.string.notifications, selectedTab == "notifications") {
            onTabSelected("notifications")
        }
        NavItem(R.drawable.explore_icon, R.string.explore, selectedTab == "explore") {
            onTabSelected("explore")
        }
    }
}

@Composable
fun NavItem(
    @DrawableRes icon: Int,
    @StringRes label: Int,
    selected: Boolean,
    navigateTo: () -> Unit
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(selected) {
        if (selected) {
            scale.snapTo(1f)
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
            )
            scale.animateTo(
                targetValue = 1.05f,
                animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing)
            )
        }
    }


    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { navigateTo() }
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(icon),
            contentDescription = stringResource(label),
            tint = if (selected) enabledButtonContainer else unfocusedNavBarItem
        )
        Text(
            text = stringResource(label),
            color = if (selected) Color.Black else unfocusedNavBarItem,
            fontFamily = MontserratFamily,
            fontSize = 9.sp,
            lineHeight = 11.sp,
            letterSpacing = 0.3.sp,
            fontWeight = FontWeight.Medium
        )
    }
}