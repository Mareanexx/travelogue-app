package ru.mareanexx.feature_auth.presentation.screens.create_profile.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.mareanexx.common.ui.theme.choosePicBackground
import ru.mareanexx.common.ui.theme.mainBorderColor
import ru.mareanexx.feature_auth.R
import ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form.CreateProfileForm

@Composable
fun ProfileAvatarRow(
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    formState: State<CreateProfileForm>
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { launcher.launch("image/*") },
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (formState.value.avatar != null) {
            Image(
                painter = rememberAsyncImagePainter(formState.value.avatar),
                contentDescription = null,
                modifier = Modifier
                    .size(83.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        else {
            PhotoPlaceholder()
        }

        Text(
            text = stringResource(R.string.pick_photo),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun PhotoPlaceholder() {
    Box(
        modifier = Modifier
            .size(83.dp)
            .background(color = choosePicBackground, shape = CircleShape)
            .border(width = 2.dp, color = mainBorderColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(R.drawable.add_a_photo_icon),
            contentDescription = null, tint = mainBorderColor
        )
    }
}