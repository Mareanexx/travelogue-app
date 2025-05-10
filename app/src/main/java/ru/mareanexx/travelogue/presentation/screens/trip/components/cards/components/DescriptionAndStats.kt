package ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.presentation.screens.trip.components.EditMapPointButton
import ru.mareanexx.travelogue.presentation.theme.likedIcon
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText
import ru.mareanexx.travelogue.presentation.theme.unfocusedNavBarItem

@Composable
fun DescriptionAndStatsBlock(
    mapPointData: State<MapPointWithPhotos?>, onOpenEditSheet: () -> Unit,
    onAddLike: () -> Unit, onRemoveLike: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = mapPointData.value!!.mapPoint.description, color = profileSecondaryText,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Row(
            modifier = Modifier.padding(vertical = 15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {

                Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(remember { MutableInteractionSource() }, null) {
                        if (!mapPointData.value!!.mapPoint.isLiked) onAddLike() else onRemoveLike()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp), tint = if (!mapPointData.value!!.mapPoint.isLiked) unfocusedNavBarItem else likedIcon,
                        painter = painterResource(if (!mapPointData.value!!.mapPoint.isLiked) R.drawable.like_icon else R.drawable.like_notif),
                        contentDescription = stringResource(R.string.cd_points_settings)
                    )
                    Text(
                        text = "${mapPointData.value!!.mapPoint.likesNumber}", style = MaterialTheme.typography.labelMedium,
                        color = unfocusedNavBarItem
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(32.dp), tint = unfocusedNavBarItem,
                        painter = painterResource(R.drawable.chat_icon), contentDescription = stringResource(R.string.cd_points_settings)
                    )
                    Text(
                        text = "${mapPointData.value!!.mapPoint.commentsNumber}", style = MaterialTheme.typography.labelMedium,
                        color = unfocusedNavBarItem
                    )
                }
            }
            EditMapPointButton(onOpenEditSheet = onOpenEditSheet)
        }
    }
}