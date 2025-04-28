package ru.mareanexx.travelogue.presentation.screens.explore.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.domain.explore.entity.InspiringProfileResponse
import ru.mareanexx.travelogue.presentation.screens.follows.components.StartFollowButton
import ru.mareanexx.travelogue.presentation.screens.follows.components.UnfollowButton
import ru.mareanexx.travelogue.presentation.theme.MontserratFamily
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText

@Composable
fun InspiringTravelerBigCard(traveler: InspiringProfileResponse) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp).height(190.dp)
            .shadow(elevation = 5.dp, Shapes.small, ambientColor = Color.Black.copy(alpha = 0.3f), spotColor = Color.Black.copy(alpha = 0.2f))
            .background(Color.White).clip(Shapes.small)
    ) {
        Box(modifier = Modifier.fillMaxHeight().width(86.dp)) {
            AsyncImage(
                modifier = Modifier.fillMaxHeight().width(64.dp),
                model = "${BuildConfig.API_FILES_URL}${traveler.coverPhoto}",
                placeholder = painterResource(R.drawable.cover_placeholder),
                error = painterResource(R.drawable.cover_placeholder),
                contentDescription = stringResource(R.string.cd_cover_photo),
                contentScale = ContentScale.Crop,
            )
            AsyncImage(
                modifier = Modifier.padding(top = 20.dp).clip(CircleShape)
                    .size(42.dp).align(Alignment.TopEnd).border(1.dp, Color.White, CircleShape),
                model = "${BuildConfig.API_FILES_URL}${traveler.avatar}",
                placeholder = painterResource(R.drawable.avatar_placeholder),
                error = painterResource(R.drawable.avatar_placeholder),
                contentDescription = stringResource(R.string.cd_avatar_photo),
                contentScale = ContentScale.Crop,
            )
        }
        Column(modifier = Modifier.padding(top = 30.dp, bottom = 20.dp, start = 15.dp, end = 60.dp)) {
            Text(text = traveler.username, style = MaterialTheme.typography.titleSmall, color = profilePrimaryText)
            Text(
                modifier = Modifier.padding(top = 3.dp),
                text = traveler.bio, style = MaterialTheme.typography.bodySmall,
                color = profileSecondaryText,
                maxLines = 1
            )
            TravelerStatisticsRow(traveler)
            if (traveler.isFollowing) {
                UnfollowButton(onUnfollowClicked = { TODO("Реализовать follow по нажатию") })
            } else {
                StartFollowButton(onStartFollowClicked = { TODO("Реализовать unfollow") })
            }
        }
    }
}

@Composable
fun TravelerStatisticsRow(traveler: InspiringProfileResponse) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OneStatisticsColumn(traveler.tripsNumber, R.string.trips)
        OneStatisticsColumn(traveler.followersNumber, R.string.followers)
        OneStatisticsColumn(traveler.followingNumber, R.string.followings)
    }
}

@Composable
fun OneStatisticsColumn(value: Int, @StringRes statName: Int) {
    Column {
        Text(
            text = "$value", style = MaterialTheme.typography.titleSmall,
            color = profilePrimaryText, fontSize = 18.sp
        )
        Text(
            text = stringResource(statName),
            style = TextStyle(color = profileSecondaryText, fontSize = 10.sp,
                fontWeight = FontWeight.Medium, fontFamily = MontserratFamily, lineHeight = 11.sp
            )
        )
    }
}