package ru.mareanexx.travelogue.presentation.screens.trip.components.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.presentation.screens.trip.components.TagGrid
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun TripDescriptionStartBlock(tripData: TripWithMapPoints) {
    Column(
        modifier = Modifier.fillMaxHeight().widthIn(min = 250.dp, max = 300.dp).background(primaryText).padding(25.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.width(IntrinsicSize.Max),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(26.dp), painter = painterResource(R.drawable.quote_icon),
                contentDescription = null, tint = Color.White
            )
            Text(
                text = tripData.trip.description, color = Color.White,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        TagGrid(tripData)

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.swipe_cd), color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium)
            )
            Icon(
                modifier = Modifier.size(15.dp),
                painter = painterResource(R.drawable.arrow_forward_icon),
                contentDescription = stringResource(R.string.swipe_cd), tint = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}