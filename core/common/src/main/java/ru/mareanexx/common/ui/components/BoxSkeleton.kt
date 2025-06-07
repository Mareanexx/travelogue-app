package ru.mareanexx.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun BoxSkeleton(modifier: Modifier = Modifier, height: Int, shape: Shape = RoundedCornerShape(5.dp)) {
    Box(modifier = modifier
        .height(height.dp)
        .background(color = Color.LightGray, shape = shape)
    )
}