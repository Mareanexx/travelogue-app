package ru.mareanexx.travelogue.presentation.screens.trip.components.trip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.presentation.theme.Shapes

@Composable
fun TripHeader(
    username: String, avatar: String,
    tripData: TripWithMapPoints,
    navigateBack: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
                .padding(start = 15.dp, end = 55.dp)
        ) {
            Button(
                shape = Shapes.small,
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = navigateBack
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.arrow_left),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.get_back_cd)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onNavigateToOthersProfile(tripData.trip.profileId) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    AsyncImage(
                        modifier = Modifier.size(17.dp).clip(CircleShape),
                        model = "${BuildConfig.API_FILES_URL}${avatar}",
                        placeholder = painterResource(R.drawable.avatar_placeholder),
                        error = painterResource(R.drawable.avatar_placeholder),
                        contentDescription = stringResource(R.string.cd_avatar_photo),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = username, color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 14.sp, lineHeight = 14.sp),
                    )
                }
                Text(
                    text = tripData.trip.name, color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}