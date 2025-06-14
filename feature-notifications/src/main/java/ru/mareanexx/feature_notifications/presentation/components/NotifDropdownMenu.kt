package ru.mareanexx.feature_notifications.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.deleteText
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.feature_notifications.R


@Composable
fun NotificationsDropdownMenuAndButton(
    onDeleteNotifications: () -> Unit
) {
    val menuExpanded = remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Box {
            Button(
                shape = Shapes.small,
                modifier = Modifier.size(34.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryText, contentColor = Color.White),
                onClick = { menuExpanded.value = !menuExpanded.value }
            ) {
                Icon(
                    painter = painterResource(ru.mareanexx.core.common.R.drawable.more_points_icon),
                    contentDescription = stringResource(ru.mareanexx.core.common.R.string.cd_points_settings),
                    tint = Color.White
                )
            }
            DropdownMenu(
                shape = Shapes.small,
                containerColor = Color.White,
                expanded = menuExpanded.value,
                offset = DpOffset(x = 0.dp, y = 0.dp),
                onDismissRequest = { menuExpanded.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.mark_all_as_read), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                    onClick = { }, // TODO("Нужно сделать убирание галочек у всех уведомлений")
                    trailingIcon = { Icon(painter = painterResource(R.drawable.read_notifs), contentDescription = null, tint = primaryText) }
                )
                HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.clear_all_notifications), style = MaterialTheme.typography.bodyMedium, color = deleteText, fontWeight = FontWeight.SemiBold) },
                    onClick = { onDeleteNotifications() },
                    trailingIcon = { Icon(painter = painterResource(ru.mareanexx.core.common.R.drawable.delete_icon), contentDescription = null, tint = deleteText) }
                )
            }
        }
    }
}