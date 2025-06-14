package ru.mareanexx.feature_profiles.presentation.screens.profile.components.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.feature_profiles.R


@Composable
fun ProfileButtonsRow(onOpenModalSheet: (ProfileBottomSheetType) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileButton(
            weightModifier = Modifier.weight(2.5f),
            icon = ru.mareanexx.core.common.R.drawable.plus_icon,
            text = R.string.add_trip,
            containerColor = primaryText,
            contentColor = Color.White,
            onClick = { onOpenModalSheet(ProfileBottomSheetType.TripType) }
        )
        ProfileButton(
            weightModifier = Modifier.weight(2.5f),
            icon = R.drawable.stats_icon,
            text = R.string.travel_stats,
            borderColor = Color(0xFFE2E2E2)
        ) { } // TODO("Сделать toast с надписью нереализовано еще")
        SettingsButton(Modifier.weight(0.5f), onOpenModalSheet)
    }
}

@Composable
fun ProfileButton(
    weightModifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    @StringRes text: Int,
    containerColor: Color = Color.White,
    contentColor: Color = primaryText,
    borderColor: Color = containerColor,
    onClick: () -> Unit
) {
    Button(
        modifier = weightModifier.height(34.dp),
        shape = Shapes.small,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(contentColor = contentColor, containerColor = containerColor),
        border = BorderStroke(width = 2.dp, color = borderColor)
    ) {
        if (icon != null) Icon(painter = painterResource(icon), contentDescription = null)
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = stringResource(text),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            lineHeight = 12.sp
        )
    }
}

@Composable
fun SettingsButton(
    weightModifier: Modifier,
    onOpenModalSheet: (ProfileBottomSheetType) -> Unit
) {
    val expandedMenu = remember { mutableStateOf(false) }

    Box(modifier = weightModifier) {
        Button(
            modifier = Modifier.size(34.dp),
            shape = Shapes.small,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(contentColor = primaryText, containerColor = Color.White),
            border = BorderStroke(width = 2.dp, color = Color(0xFFE2E2E2)),
            onClick = { expandedMenu.value = !expandedMenu.value }
        ) {
            Icon(painter = painterResource(ru.mareanexx.core.common.R.drawable.settings_icon), contentDescription = null)
        }
        DropdownMenu(
            shape = Shapes.small,
            expanded = expandedMenu.value,
            containerColor = Color.White,
            onDismissRequest = { expandedMenu.value = !expandedMenu.value }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.edit_profile), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = {
                    onOpenModalSheet(ProfileBottomSheetType.EditProfile)
                    expandedMenu.value = false
                },
                trailingIcon = { Icon(painter = painterResource(ru.mareanexx.core.common.R.drawable.edit_pen_icon), contentDescription = null, tint = primaryText) }
            )
            HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.account), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = {
                    onOpenModalSheet(ProfileBottomSheetType.Account)
                    expandedMenu.value = false
                },
                trailingIcon = { Icon(painter = painterResource(ru.mareanexx.core.common.R.drawable.edit_icon), contentDescription = null, tint = primaryText) }
            )
        }
    }
}