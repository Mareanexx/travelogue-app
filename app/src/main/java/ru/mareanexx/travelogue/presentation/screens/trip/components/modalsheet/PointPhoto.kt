package ru.mareanexx.travelogue.presentation.screens.trip.components.modalsheet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.domain.mappoint.entity.PointPhoto
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form.MapPointForm
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.mainBorderColor
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.utils.FileUtils.uriToFile
import java.io.File

@Composable
fun PointPhotosRow(
    mapPointForm: MapPointForm,
    addPhoto: (File) -> Unit, deletePhoto: (File) -> Unit,
    onDeleteAndAddToDeleted: (PointPhoto) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(uri, context)
            addPhoto(file)
        }
    }

    LazyRow(
        contentPadding = PaddingValues(vertical = 10.dp),
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        item { AddPointPhotoButton(addPhoto = { launcher.launch("image/*") } ) }
        items(mapPointForm.serverPhotos) { photo ->
            PointPhotoImage(photo = null, pointPhoto = photo, removePhoto = { onDeleteAndAddToDeleted(photo) } )
        }
        items(mapPointForm.photos) { photo ->
            PointPhotoImage(photo = photo, removePhoto = { deletePhoto(photo) })
        }
    }
}

@Composable
fun AddPointPhotoButton(addPhoto: () -> Unit) {
    Box(
        modifier = Modifier.size(97.dp).border(2.dp, mainBorderColor, Shapes.extraSmall)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { addPhoto() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.add_photo_icon), modifier = Modifier.size(28.dp),
            tint = mainBorderColor, contentDescription = stringResource(R.string.add_map_point_title)
        )
    }
}

@Composable
fun PointPhotoImage(photo: File?, pointPhoto: PointPhoto? = null, removePhoto: () -> Unit) {
    Box(modifier = Modifier.size(102.dp)) {
        AsyncImage(
            modifier = Modifier.align(Alignment.BottomStart).size(97.dp).clip(Shapes.extraSmall),
            model = photo ?: "${BuildConfig.API_FILES_URL}${pointPhoto?.filePath}",
            error = painterResource(R.drawable.cover_placeholder),
            placeholder = painterResource(R.drawable.cover_placeholder),
            contentDescription = stringResource(R.string.point_photo_cd),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.align(Alignment.TopEnd).background(primaryText, CircleShape)
                .padding(2.dp).clickable { removePhoto() }
        ) {
            Icon(
                imageVector = Icons.Default.Close, modifier = Modifier.size(18.dp),
                contentDescription = null, tint = Color.White
            )
        }
    }
}