package ru.mareanexx.feature_profiles.presentation.screens.profile.components.trips

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import ru.mareanexx.common.ui.components.interactive.AreYouSureDialog
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet.TripSheetContent
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.TripTypifiedDialog
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.TripsEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsEventHandler(
    eventFlow: Flow<TripsEvent>,
    onDeleteConfirmed: (Int) -> Unit,
    enteredTagName: String,
    onTagNameChanged: (String) -> Unit, onAddTagClicked: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val dialogType = remember { mutableStateOf(TripTypifiedDialog.Delete) }
    val deletedTrip = remember { mutableIntStateOf(-1) }
    var showEditTripSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        eventFlow.collect { tripsEvent ->
            when (tripsEvent) {
                is TripsEvent.ShowToast -> {
                    Toast.makeText(context, tripsEvent.message, Toast.LENGTH_SHORT).show()
                }
                is TripsEvent.ShowTypifiedDialog -> {
                    deletedTrip.intValue = tripsEvent.id
                    dialogType.value = tripsEvent.type
                    showDialog = true
                }
                is TripsEvent.ShowEditBottomSheet -> { showEditTripSheet = tripsEvent.showing }
            }
        }
    }

    if (showDialog) {
        when(dialogType.value) {
            TripTypifiedDialog.Delete -> {
                AreYouSureDialog(
                    additional = R.string.this_trip_variant_del,
                    onCancelClicked = { showDialog = false },
                    onDeleteClicked = {
                        onDeleteConfirmed(deletedTrip.intValue)
                        showDialog = false
                    },
                )
            }
            TripTypifiedDialog.CreateTag -> {
                CreateTagDialog(
                    enteredTagName = enteredTagName,
                    onTagNameChanged = onTagNameChanged,
                    onCancelClicked = { showDialog = false },
                    onAddTagClicked = onAddTagClicked
                )
            }
        }
    }

    if (showEditTripSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            containerColor = Color.White,
            onDismissRequest = { showEditTripSheet = false }
        ) {
            TripSheetContent(
                buttonText = ru.mareanexx.core.common.R.string.save_changes, titleText = R.string.edit_trip,
                onAction = {
                    viewModel -> viewModel.updateTrip()
                    showEditTripSheet = false
                },
                isEditing = true
            )
        }
    }
}