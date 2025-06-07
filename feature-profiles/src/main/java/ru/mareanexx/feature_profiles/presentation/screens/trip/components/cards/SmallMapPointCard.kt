package ru.mareanexx.feature_profiles.presentation.screens.trip.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.mapBoxBackground
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.core.common.R
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.presentation.screens.trip.components.DayRow
import ru.mareanexx.feature_profiles.presentation.screens.trip.components.InteractableLikesStatButton
import ru.mareanexx.feature_profiles.presentation.screens.trip.components.MapPointStatsItem
import java.time.LocalDate

@Composable
fun MapPointCard(
    tripStartDate: LocalDate, mapPointData: MapPointWithPhotos,
    onExpandCard: (MapPointWithPhotos) -> Unit,
    onAddNewLike: (mapPointId: Int) -> Unit, onRemoveLike: (mapPointId: Int) -> Unit
) {
    Column(
        modifier = Modifier.width(212.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onExpandCard(mapPointData) }
    ) {

        DayRow(tripStartDate, mapPointData.mapPoint.arrivalDate)

        Column(
            modifier = Modifier
                .shadow(5.dp, Shapes.small, ambientColor = Color.Black.copy(alpha = 0.3f), spotColor = Color.Black.copy(alpha = 0.4f))
                .clip(Shapes.small).background(Color.White)
        ) {
            Box(modifier = Modifier.height(135.dp)) {
                AsyncImage(
                    modifier = Modifier.fillMaxHeight(),
                    model = "${ApiConfig.apiFilesUrl}${mapPointData.photos.getOrNull(0)?.filePath}",
                    placeholder = painterResource(R.drawable.cover_placeholder),
                    error = painterResource(R.drawable.cover_placeholder),
                    contentDescription = stringResource(R.string.cd_cover_photo),
                    contentScale = ContentScale.Crop
                )

                MapPointInnerShadow(Modifier.align(Alignment.TopCenter))

                Text(
                    modifier = Modifier.align(Alignment.BottomStart).padding(15.dp),
                    text = mapPointData.mapPoint.name, color = Color.White,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.White)
                    .padding(horizontal = 11.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InteractableLikesStatButton(mapPointData, onAddNewLike, onRemoveLike)
                MapPointStatsItem(ru.mareanexx.feature_profiles.R.drawable.chat_icon, mapPointData.mapPoint.commentsNumber)
                MapPointStatsItem(R.drawable.camera_icon, mapPointData.mapPoint.photosNumber)
                MapPointStatsItem(ru.mareanexx.feature_profiles.R.drawable.camping_icon, 0) // TODO() потом придумать значение
            }
        }
    }
}

@Composable
fun MapPointInnerShadow(alignModifier: Modifier) {
    Box(modifier = alignModifier.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    mapBoxBackground.copy(alpha = 0f), mapBoxBackground.copy(alpha = 0f), mapBoxBackground.copy(alpha = 0f),
                    mapBoxBackground.copy(alpha = 0.3f), mapBoxBackground.copy(alpha = 0.7f)
                )
            )
        )
    )
}