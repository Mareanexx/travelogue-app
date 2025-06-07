package ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import ru.mareanexx.common.ui.components.SupportingText
import ru.mareanexx.common.ui.components.interactive.CheckFieldsButton
import ru.mareanexx.common.ui.components.interactive.CustomOutlinedTextField
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.ui.theme.profilePrimaryText
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.common.utils.FileUtils.uriToFile
import ru.mareanexx.core.common.R
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.ProfileViewModel
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.DeletedType
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.state.ProfileUiState


@Composable
fun EditProfileSheetContent(viewModel: ProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val profileData = viewModel.profileData.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val updatedProfileData = viewModel.updatedProfileData.collectAsState()

    if (profileData.value == null) return

    val avatarLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(uri, context)
            viewModel.onAvatarSelected(file)
        }
    }

    val coverLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(uri, context)
            viewModel.onCoverPhotoSelected(file)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 15.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
            text = stringResource(R.string.profile_settings),
            style = MaterialTheme.typography.displaySmall,
            color = profilePrimaryText, textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(170.dp).clip(Shapes.medium),
                model = if (updatedProfileData.value.wasCoverReuploaded) updatedProfileData.value.newCoverPhoto else "${ApiConfig.apiFilesUrl}${profileData.value!!.coverPhoto}",
                placeholder = painterResource(R.drawable.cover_placeholder),
                error = painterResource(R.drawable.cover_placeholder),
                contentDescription = stringResource(R.string.cd_cover_photo),
                contentScale = ContentScale.Crop
            )
            Row(Modifier.align(Alignment.Center), horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                ImageActionIconButton(
                    icon = R.drawable.add_photo_icon,
                    onClick = { coverLauncher.launch("image/*") }
                )
                if ((updatedProfileData.value.newCoverPhoto != null || !updatedProfileData.value.wasCoverReuploaded)
                        || updatedProfileData.value.newCoverPhoto != null) {
                    ImageActionIconButton(
                        icon = R.drawable.delete_icon,
                        onClick = { viewModel.showDeleteDialog(DeletedType.Cover) }
                    )
                }
            }
        }
        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                AsyncImage(
                    modifier = Modifier.size(80.dp).clip(CircleShape),
                    model = if(updatedProfileData.value.wasAvatarReuploaded) updatedProfileData.value.avatar else "${ApiConfig.apiFilesUrl}${profileData.value!!.avatar}",
                    contentDescription = stringResource(R.string.cd_avatar_photo),
                    placeholder = painterResource(R.drawable.avatar_placeholder),
                    error = painterResource(R.drawable.avatar_placeholder),
                    contentScale = ContentScale.Crop
                )
                if ((updatedProfileData.value.avatar != null || !updatedProfileData.value.wasAvatarReuploaded)
                    || updatedProfileData.value.avatar != null) {
                    ImageActionIconButton(
                        size = 30.dp, innerPadding = 4.dp, backgroundColor = primaryText,
                        icon = R.drawable.delete_icon, backgroundShape = Shapes.extraSmall,
                        onClick = { viewModel.showDeleteDialog(DeletedType.Avatar) }
                    )
                }
            }
            Text(
                modifier = Modifier.padding(vertical = 15.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { avatarLauncher.launch("image/*") },
                text = stringResource(R.string.hint_change_avatar),
                style = MaterialTheme.typography.labelMedium, color = primaryText
            )
        }
        CustomOutlinedTextField(
            textRes = R.string.fullname,
            value = updatedProfileData.value.fullName,
            onValueChanged = { viewModel.onFullnameChanged(it) },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            supportingText = { SupportingText(R.string.fullname_requirement) }
        )
        CustomOutlinedTextField(
            textRes = R.string.username,
            value = updatedProfileData.value.username,
            onValueChanged = { viewModel.onUsernameChanged(it) },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            supportingText = { SupportingText(R.string.username_requirement) }
        )
        CustomOutlinedTextField(
            textRes = R.string.bio,
            value = updatedProfileData.value.bio,
            onValueChanged = { viewModel.onBioChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(30.dp))
        CheckFieldsButton(
            textRes = R.string.save_changes,
            enabled = updatedProfileData.value.buttonEnabled,
            showLoading = uiState.value == ProfileUiState.IsLoading
        ) { viewModel.updateProfile() }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun ImageActionIconButton(
    backgroundColor: Color = Color.Black.copy(alpha = 0.4f),
    backgroundShape: Shape = Shapes.medium,
    size: Dp = 50.dp,
    innerPadding: Dp = 10.dp,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier.size(size)
            .background(color = backgroundColor, shape = backgroundShape)
            .padding(innerPadding)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        painter = painterResource(icon), contentDescription = null,
        tint = Color.White
    )
}