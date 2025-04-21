package ru.mareanexx.travelogue.presentation.screens.profile.components.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.presentation.screens.profile.components.modalsheet.AccountSettingsSheetContent
import ru.mareanexx.travelogue.presentation.screens.profile.components.modalsheet.EditProfileSheetContent
import ru.mareanexx.travelogue.presentation.screens.profile.components.skeleton.ProfileContentSkeleton
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.ProfileUiState
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.ProfileViewModel
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText

enum class ProfileSettingsSheet {
    None, EditProfile, Account, AddTrip
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    navigateToStartScreen: () -> Unit,
    onLoadTrips: () -> Unit, viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val profileData = viewModel.profileData.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val sheetExpanded = viewModel.sheetExpandedState.collectAsState()
    val bottomSheetType = viewModel.sheetType.collectAsState()

    when (uiState.value) {
        is ProfileUiState.Init -> {}
        is ProfileUiState.ShowToast -> {
            Toast.makeText(LocalContext.current, (uiState.value as ProfileUiState.ShowToast).message, Toast.LENGTH_SHORT).show()
        }
        is ProfileUiState.IsLoading -> ProfileContentSkeleton()
        ProfileUiState.Showing -> {
            onLoadTrips()
            ProfileLoadedContent(profileData, onOpenModalSheet = { type ->
                viewModel.changeBottomSheetType(type)
            })
        }
    }
    if (sheetExpanded.value) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            containerColor = Color.White,
            onDismissRequest = { viewModel.closeBottomSheet() }
        ) {
            when(bottomSheetType.value) {
                ProfileSettingsSheet.None -> {}
                ProfileSettingsSheet.EditProfile -> EditProfileSheetContent()
                ProfileSettingsSheet.Account -> AccountSettingsSheetContent(navigateToStartScreen, { viewModel.closeBottomSheet() })
                ProfileSettingsSheet.AddTrip -> TODO()
            }
        }
    }
}

@Composable
fun ProfileLoadedContent(profileData: State<ProfileDto?>, onOpenModalSheet: (ProfileSettingsSheet) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth().height(170.dp),
            model = "${BuildConfig.API_FILES_URL}${profileData.value!!.coverPhoto}",
            placeholder = painterResource(R.drawable.cover_placeholder),
            error = painterResource(R.drawable.cover_placeholder),
            contentDescription = stringResource(R.string.cd_cover_photo),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.padding(start = 25.dp, top = 15.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AsyncImage(
                model = "${BuildConfig.API_FILES_URL}${profileData.value!!.avatar}",
                contentDescription = stringResource(R.string.cd_avatar_photo),
                placeholder = painterResource(R.drawable.avatar_placeholder),
                error = painterResource(R.drawable.avatar_placeholder),
                modifier = Modifier.size(80.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(top = 15.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = profileData.value!!.username,
                    style = MaterialTheme.typography.titleSmall,
                    color = profilePrimaryText
                )
                Text(
                    text = profileData.value!!.bio,
                    style = MaterialTheme.typography.bodySmall,
                    color = profileSecondaryText
                )
            }
        }

        ProfileStatisticsBlock(
            tripsNumber = profileData.value!!.tripsNumber,
            followersNumber = profileData.value!!.followersNumber,
            followingsNumber = profileData.value!!.followingNumber
        )

        Column(modifier = Modifier.padding(horizontal = 15.dp).padding(top = 12.dp, bottom = 15.dp)) {
            ProfileButtonsRow(onOpenModalSheet = onOpenModalSheet)
        }
    }
}