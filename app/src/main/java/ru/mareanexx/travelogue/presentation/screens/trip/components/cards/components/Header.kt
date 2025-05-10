package ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.dateTextFormatter
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.timeTextFormatter
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun ExpandedMapPointCardHeader(alphaFloat: Float, mapPointData: State<MapPointWithPhotos?>) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .graphicsLayer { alpha = alphaFloat }.background(primaryText)
            .padding(vertical = 18.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = mapPointData.value!!.mapPoint.name, color = Color.White,
            style = MaterialTheme.typography.titleSmall.copy(letterSpacing = 0.4.sp, lineHeight = 22.sp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = mapPointData.value!!.mapPoint.arrivalDate.format(dateTextFormatter),
                style = MaterialTheme.typography.bodySmall.copy(Color.White, fontSize = 11.sp, fontWeight = FontWeight.Normal)
            )
            Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.8f)))
            Text(
                text = mapPointData.value!!.mapPoint.arrivalDate.format(timeTextFormatter),
                style = MaterialTheme.typography.bodySmall.copy(Color.White, fontSize = 11.sp, fontWeight = FontWeight.Normal)
            )
            Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.8f)))
            Text(
                text = "Day 1",
                style = MaterialTheme.typography.bodySmall.copy(Color.White, fontSize = 11.sp, fontWeight = FontWeight.Normal)
            )
        }
    }
}