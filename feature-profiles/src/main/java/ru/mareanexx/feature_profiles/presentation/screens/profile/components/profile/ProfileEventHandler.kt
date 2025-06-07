package ru.mareanexx.feature_profiles.presentation.screens.profile.components.profile

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.ui.components.interactive.AreYouSureDialog
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet.AccountSettingsSheetContent
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet.EditProfileSheetContent
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet.TripSheetContent
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet.TripTypeSheetContent
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.DeletedType
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.ProfileEvent

enum class ProfileBottomSheetType {
    None, EditProfile, Account, TripType, AddTrip
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEventHandler(
    eventFlow: Flow<ProfileEvent>,
    navigateToStartScreen: () -> Unit,
    onDeleteImageConfirmed: (DeletedType) -> Unit
) {
    val context = LocalContext.current
    val bottomSheetType = remember { mutableStateOf(ProfileBottomSheetType.None) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showDialog = remember { mutableStateOf<DeletedType?>(null) }

    LaunchedEffect(Unit) {
        eventFlow.collect { profileEvent ->
            when (profileEvent) {
                is ProfileEvent.ShowToast -> {
                    Toast.makeText(context, profileEvent.message, Toast.LENGTH_SHORT).show()
                }
                is ProfileEvent.ShowTypifiedBottomSheet -> {
                    bottomSheetType.value = profileEvent.type
                    showBottomSheet = profileEvent.showing
                }
                is ProfileEvent.ShowDeleteDialog -> {
                    showDialog.value = profileEvent.deletedType
                }
            }
        }
    }

    showDialog.value?.let {
        AreYouSureDialog(
            additional = it.text,
            onCancelClicked = { showDialog.value = null },
            onDeleteClicked = { onDeleteImageConfirmed(it); showDialog.value = null },
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            containerColor = Color.White,
            onDismissRequest = { showBottomSheet = false }
        ) {
            when(bottomSheetType.value) {
                ProfileBottomSheetType.None -> {}
                ProfileBottomSheetType.EditProfile -> EditProfileSheetContent()
                ProfileBottomSheetType.Account -> AccountSettingsSheetContent(
                    navigateToStartScreen,
                    closeModalBottomSheet = { showBottomSheet = false },
                )
                ProfileBottomSheetType.TripType -> TripTypeSheetContent(
                    onChangeBottomSheetType = { bottomSheetType.value = ProfileBottomSheetType.AddTrip }
                )
                ProfileBottomSheetType.AddTrip -> TripSheetContent(
                    isEditing = false,
                    buttonText = R.string.create_trip,
                    titleText = R.string.new_trip_title,
                    onAction = {
                        viewModel -> viewModel.uploadNewTrip()
                        showBottomSheet = false
                    }
                )
            }
        }
    }
}