package ru.mareanexx.travelogue.presentation.screens.profile.components.profile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.presentation.components.CardInnerDarkening
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText

@Composable
fun ProfileCoverPhoto(profileData: ProfileDto?) {
    Box(modifier = Modifier.fillMaxWidth().height(170.dp)) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = "${BuildConfig.API_FILES_URL}${profileData?.coverPhoto}",
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = stringResource(R.string.cd_cover_photo),
            contentScale = ContentScale.Crop
        )
        CardInnerDarkening(Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun ProfileHeaderBlock(profileData: ProfileDto?, visible: Boolean) {
    val avatarAnimSize by animateDpAsState(if (visible) 50.dp else 70.dp, label = "avatarAnimSize")
    val horizPaddingAnim by animateDpAsState(if (visible) 15.dp else 5.dp, label = "paddingAnim")

    Row(
        modifier = Modifier.fillMaxWidth().background(Color.White)
            .padding(start = 15.dp, top = horizPaddingAnim, bottom = horizPaddingAnim),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "${BuildConfig.API_FILES_URL}${profileData!!.avatar}",
            contentDescription = stringResource(R.string.cd_avatar_photo),
            placeholder = painterResource(R.drawable.avatar_placeholder),
            error = painterResource(R.drawable.avatar_placeholder),
            modifier = Modifier.size(avatarAnimSize).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier) {
            Text(
                modifier = Modifier.padding(bottom = 7.dp),
                text = profileData.username,
                style = MaterialTheme.typography.titleSmall,
                color = profilePrimaryText
            )
            Text(
                text = profileData.bio,
                style = MaterialTheme.typography.bodySmall,
                color = profileSecondaryText
            )
        }
    }
}

@Composable
fun ProfileFollowersAndButtons(
    profileData: State<ProfileDto?>, onOpenModalSheet: (ProfileBottomSheetType) -> Unit,
    navigateToFollows: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        ProfileStatisticsBlock(
            tripsNumber = profileData.value!!.tripsNumber,
            followersNumber = profileData.value!!.followersNumber,
            followingsNumber = profileData.value!!.followingNumber,
            navigateToFollows = { navigateToFollows(profileData.value!!.username, "me") }
        )

        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            ProfileButtonsRow(onOpenModalSheet = onOpenModalSheet)
        }
    }
}