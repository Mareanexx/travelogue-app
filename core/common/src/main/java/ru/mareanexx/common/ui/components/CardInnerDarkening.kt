package ru.mareanexx.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import ru.mareanexx.common.ui.theme.mapBoxBackground

@Composable
fun CardInnerDarkening(alignModifier: Modifier) {
    Box(modifier = alignModifier.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    mapBoxBackground.copy(alpha = 0.0f), mapBoxBackground.copy(alpha = 0.0f), mapBoxBackground.copy(alpha = 0.1f),
                    mapBoxBackground.copy(alpha = 0.3f), mapBoxBackground.copy(alpha = 0.6f), mapBoxBackground.copy(alpha = 0.8f)
                )
            )
        )
    )
}