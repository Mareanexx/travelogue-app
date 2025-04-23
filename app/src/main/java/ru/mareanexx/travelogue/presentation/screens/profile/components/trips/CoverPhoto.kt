package ru.mareanexx.travelogue.presentation.screens.profile.components.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.choosePicBackground
import ru.mareanexx.travelogue.presentation.theme.mainBorderColor
import java.io.File


@Composable
fun CoverPhotoImage(coverPhoto: File) {
    Box(modifier = Modifier.clip(Shapes.medium)) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth().height(206.dp),
            contentDescription = "${coverPhoto.name} selected pic",
            model = coverPhoto,
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentScale = ContentScale.Crop
        )
        Icon(
            modifier = Modifier.align(Alignment.Center).size(50.dp)
                .background(Color.Black.copy(alpha = 0.4f), shape = Shapes.medium)
                .padding(10.dp),
            painter = painterResource(R.drawable.add_photo_icon), contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun CoverPhotoChooserBox() {
    Box(
        modifier = Modifier.fillMaxWidth().height(206.dp)
            .background(choosePicBackground, Shapes.medium)
            .border(width = 2.dp, color = mainBorderColor, shape = Shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.background(mainBorderColor, Shapes.small).padding(horizontal = 11.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.add_photo_icon),
                contentDescription = null, tint = Color.White
            )
            Text(
                text = stringResource(R.string.pick_a_cover_photo),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White, fontWeight = FontWeight.Bold
            )
        }
    }
}