package ru.mareanexx.feature_profiles.presentation.screens.trip.components.cards.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.likedIcon
import ru.mareanexx.common.ui.theme.profileSecondaryText
import ru.mareanexx.common.ui.theme.unfocusedNavBarItem
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.presentation.screens.trip.components.EditMapPointButton

@Composable
fun DescriptionAndStatsBlock(
    profileId: String, mapPointData: State<MapPointWithPhotos?>,
    onOpenEditSheet: () -> Unit, onAddLike: () -> Unit, onRemoveLike: () -> Unit
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(mapPointData.value!!.mapPoint.isLiked) {
        if (mapPointData.value!!.mapPoint.isLiked) {
            scale.snapTo(1f)
            scale.animateTo(
                targetValue = 1.3f,
                animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing)
            )
        }
    }

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
                        modifier = Modifier.size(32.dp)
                            .graphicsLayer {
                                scaleX = scale.value
                                scaleY = scale.value
                            },
                        tint = if (!mapPointData.value!!.mapPoint.isLiked) unfocusedNavBarItem else likedIcon,
                        painter = painterResource(if (!mapPointData.value!!.mapPoint.isLiked) R.drawable.like_icon else ru.mareanexx.core.common.R.drawable.like_notif),
                        contentDescription = stringResource(ru.mareanexx.core.common.R.string.cd_points_settings)
                    )
                    Text(
                        text = "${mapPointData.value!!.mapPoint.likesNumber}", style = MaterialTheme.typography.labelMedium,
                        color = unfocusedNavBarItem
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(32.dp), tint = unfocusedNavBarItem,
                        painter = painterResource(R.drawable.chat_icon), contentDescription = stringResource(
                            ru.mareanexx.core.common.R.string.cd_points_settings
                        )
                    )
                    Text(
                        text = "${mapPointData.value!!.mapPoint.commentsNumber}", style = MaterialTheme.typography.labelMedium,
                        color = unfocusedNavBarItem
                    )
                }
            }
            if (profileId == "me") EditMapPointButton(onOpenEditSheet = onOpenEditSheet)
        }
    }
}