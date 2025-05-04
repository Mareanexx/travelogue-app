package ru.mareanexx.travelogue.presentation.screens.profile.components.trips

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
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.components.AreYouSureDialog
import ru.mareanexx.travelogue.presentation.screens.profile.components.modalsheet.TripSheetContent
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event.TripsEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsEventHandler(
    eventFlow: Flow<TripsEvent>,
    onDeleteConfirmed: (Int) -> Unit,
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val deletedTrip = remember { mutableIntStateOf(-1) }
    var showEditTripSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        eventFlow.collect { tripsEvent ->
            when (tripsEvent) {
                is TripsEvent.ShowToast -> {
                    Toast.makeText(context, tripsEvent.message, Toast.LENGTH_SHORT).show()
                }
                is TripsEvent.ShowDeleteDialog -> {
                    deletedTrip.intValue = tripsEvent.id
                    showDialog = true
                }
                is TripsEvent.ShowEditBottomSheet -> { showEditTripSheet = tripsEvent.showing }
            }
        }
    }

    if (showDialog) {
        AreYouSureDialog(
            additional = R.string.this_trip_variant_del,
            onCancelClicked = { showDialog = false },
            onDeleteClicked = {
                onDeleteConfirmed(deletedTrip.intValue)
                showDialog = false
            },
        )
    }

    if (showEditTripSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            containerColor = Color.White,
            onDismissRequest = { showEditTripSheet = false }
        ) {
            TripSheetContent(
                buttonText = R.string.save_changes, titleText = R.string.edit_trip,
                onAction = {
                    viewModel -> viewModel.updateTrip()
                    showEditTripSheet = false
                },
                isEditing = true
            )
        }
    }
}