package ru.mareanexx.common.ui.components.trip

import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.common.ui.theme.MontserratFamily
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.enabledButtonContainer
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.core.common.R
import java.time.LocalDate


@Composable
fun TripHeaderNowOnATripComponent() {
    val infiniteTransition = rememberInfiniteTransition(label = "colorAnimation")

    val animatedColor by infiniteTransition.animateColor(
        initialValue = enabledButtonContainer,
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorAnimation"
    )

    Row(
        modifier = Modifier.height(20.dp)
            .background(color = primaryText, shape = Shapes.large)
            .padding(horizontal = 8.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(7.dp).background(animatedColor, shape = CircleShape))
        Text(
            text = stringResource(R.string.now_on_a_trip),
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            fontFamily = MontserratFamily,
            lineHeight = 10.sp, color = Color.White
        )
    }
}

@Composable
fun TripStatsComponent(
    number: Int? = null,
    @StringRes textRes: Int? = null,
    date: LocalDate? = null
) {
    Column {
        Text(
            text = if(date == null) "$number" else "${date.year}",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        Text(
            text = date?.month?.name ?: stringResource(textRes!!),
            color = Color.White,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            lineHeight = 11.sp
        )
    }
}

@Composable
fun OthersTripHeaderSettings(modifier: Modifier, onSendReport: () -> Unit) {
    val expandedMenu = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button(
            onClick = { expandedMenu.value = !expandedMenu.value },
            modifier = Modifier.size(height = 20.dp, width = 35.dp),
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.3f), contentColor = Color.White),
            contentPadding = PaddingValues(0.dp)
        ) { Icon(painterResource(R.drawable.more_points_icon), stringResource(R.string.cd_points_settings)) }

        DropdownMenu(
            shape = Shapes.small,
            expanded = expandedMenu.value,
            containerColor = Color.White,
            onDismissRequest = { expandedMenu.value = !expandedMenu.value }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.report_trip), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold) },
                onClick = onSendReport,
                trailingIcon = { Icon(painterResource(R.drawable.report_icon), null, tint = primaryText) }
            )
        }
    }
}