package ru.mareanexx.travelogue.presentation.screens.trip.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagResponse
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.OneDatePicker
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.blueBackground
import ru.mareanexx.travelogue.presentation.theme.likedIcon
import ru.mareanexx.travelogue.presentation.theme.mapBoxBackground
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText
import ru.mareanexx.travelogue.presentation.theme.unfocusedIndicator
import ru.mareanexx.travelogue.presentation.theme.unfocusedNavBarItem
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NoMapPointsDivider() { HorizontalDivider(modifier = Modifier.width(27.dp)) }

@Composable
fun HasMapPointsDivider() { HorizontalDivider(modifier = Modifier.width(12.dp)) }

@Composable
fun CircleWithDividers() {
    HasMapPointsDivider()
    Box(modifier = Modifier.size(14.dp).border(2.dp, DividerDefaults.color, CircleShape))
    HasMapPointsDivider()
}

@Composable
fun StartFinishIcon(
    date: LocalDate?,
    paddingStart: Dp, paddingEnd: Dp,
    @DrawableRes icon: Int
) {
    val dateTextFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

    Column(
        modifier = Modifier.padding(start = paddingStart, end = paddingEnd, top = 32.dp).width(38.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier.clip(CircleShape).size(38.dp).background(primaryText),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(26.dp), painter = painterResource(icon),
                contentDescription = null, tint = Color.White
            )
        }
        Text(
            text = date?.format(dateTextFormatter) ?: stringResource(R.string.not_finished_yet),
            color = unfocusedNavBarItem, style = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp, lineHeight = 11.sp
            )
        )
    }
}

@Composable
fun TagGrid(tripData: TripWithMapPoints) {
    LazyHorizontalGrid(
        modifier = Modifier.height(70.dp),
        rows = GridCells.FixedSize(25.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tripData.trip.tagList ?: emptyList()) { tag ->
            TagItem(tag)
        }
    }
}

@Composable
fun TagItem(tag: NewTagResponse) {
    Row(
        modifier = Modifier
            .background(color = Color.White.copy(alpha = 0.3f), shape = Shapes.medium)
            .padding(start = 5.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.tag_icon),
            contentDescription = null, tint = Color.White
        )
        Text(
            text = tag.name, color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun DayRow(tripStartDate: LocalDate, mapPointArrivalDate: OffsetDateTime) {
    val dayNumber = Period.between(tripStartDate, mapPointArrivalDate.toLocalDate()).days + 1

    Row(
        modifier = Modifier.padding(start = 20.dp)
            .background(blueBackground, RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            .padding(start = 3.dp, end = 5.dp, top = 3.dp, bottom = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            modifier = Modifier.size(13.dp),
            painter = painterResource(R.drawable.going_icon),
            contentDescription = null, tint = Color.White
        )
        Text(
            text = "DAY $dayNumber", color = Color.White,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
        )
    }
}

@Composable
fun InteractableLikesStatButton(mapPointData: MapPointWithPhotos, onAddNewLike: (mapPointId: Int) -> Unit, onRemoveLike: (mapPointId: Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.clickable(remember { MutableInteractionSource() }, null) {
            mapPointData.mapPoint.run {
                if (isLiked) onRemoveLike(id) else onAddNewLike(id)
            }
        }
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(if (!mapPointData.mapPoint.isLiked) R.drawable.like_icon else R.drawable.like_notif),
            contentDescription = null, tint = if (!mapPointData.mapPoint.isLiked) unfocusedNavBarItem else likedIcon
        )
        Text(
            text = "${mapPointData.mapPoint.likesNumber}", color = unfocusedNavBarItem,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun MapPointStatsItem(@DrawableRes icon: Int, value: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(icon), modifier = Modifier.size(18.dp),
            contentDescription = null, tint = unfocusedNavBarItem
        )
        Text(
            text = "$value", color = unfocusedNavBarItem,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun MapBlockInnerShadow(alignModifier: Modifier) {
    Box(modifier = alignModifier.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    mapBoxBackground, mapBoxBackground.copy(alpha = 0.7f), mapBoxBackground.copy(alpha = 0.5f), mapBoxBackground.copy(alpha = 0.3f), mapBoxBackground.copy(alpha = 0.1f),
                    mapBoxBackground.copy(alpha = 0.0f), mapBoxBackground.copy(alpha = 0f), mapBoxBackground.copy(alpha = 0f), mapBoxBackground.copy(alpha = 0f), mapBoxBackground.copy(alpha = 0f), mapBoxBackground.copy(alpha = 0f), mapBoxBackground.copy(alpha = 0f),
                    mapBoxBackground.copy(alpha = 0.05f), mapBoxBackground.copy(alpha = 0.1f), mapBoxBackground.copy(alpha = 0.3f),  mapBoxBackground.copy(0.5f)
                )
            )
        )
    )
}

@Composable
fun DeleteMapPointButton(onDeleteMapPoint: () -> Unit) {
    Button(
        modifier = Modifier.height(36.dp),
        shape = Shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.error),
        onClick = onDeleteMapPoint
    ) {
        Text(text = stringResource(R.string.delete_map_point), style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun ArrivalDateTimePicker(
    weightModifier: Modifier,
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int,
    @StringRes pickerType: Int,
    value: String,
    onOpenPickerDialog: () -> Unit
) {
    Row(
        modifier = weightModifier.border(1.dp, unfocusedIndicator, Shapes.medium).padding(15.dp)
            .clickable(remember { MutableInteractionSource() }, null) { onOpenPickerDialog() },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = painterResource(icon), tint = profileSecondaryText,
            contentDescription = stringResource(iconDescription)
        )
        OneDatePicker(dateType = pickerType, value = value)
    }
}