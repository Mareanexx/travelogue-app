package ru.mareanexx.travelogue.presentation.screens.trip.components

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.trip.components.dialogs.ArrivalDatePickerDialog
import ru.mareanexx.travelogue.presentation.screens.trip.components.dialogs.ArrivalTimePickerDialog
import ru.mareanexx.travelogue.presentation.screens.trip.components.dialogs.ErrorDialog
import ru.mareanexx.travelogue.presentation.screens.trip.components.dialogs.LocationPickerDialog
import ru.mareanexx.travelogue.presentation.screens.trip.components.modalsheet.MapPointSheetContent
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.BottomSheetType
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.DialogType
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.TripEvent
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form.MapPointForm


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripEventHandler(
    mapPointForm: MapPointForm,
    eventFlow: SharedFlow<TripEvent>,
    onRetry: () -> Unit, onNavigateBack: () -> Unit,
    onDateSelected: (Long) -> Unit, onTimeSelected: (Int, Int) -> Unit,
    onUpdateCoordinates: (lat: Double, lng: Double) -> Unit,
    onAddStep: () -> Unit, onEditMapPoint: () -> Unit, onClearForm: () -> Unit
) {
    val context = LocalContext.current
    val bottomSheetType = remember { mutableStateOf(BottomSheetType.AddStep) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showDialog = remember { mutableStateOf(false) }
    val dialogType = remember { mutableStateOf(DialogType.Error) }
    val showBottomSheet = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is TripEvent.ShowToast -> { Toast.makeText(context, event.message, Toast.LENGTH_SHORT) }
                is TripEvent.ShowTypifiedBottomSheet -> { bottomSheetType.value = event.type; showBottomSheet.value = true }
                is TripEvent.ShowTypifiedDialog -> { dialogType.value = event.type; showDialog.value = true }
            }
        }
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            containerColor = Color.White,
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet.value = false; onClearForm() }
        ) {
            when (bottomSheetType.value) {
                BottomSheetType.AddStep -> {
                    MapPointSheetContent(
                        title = R.string.add_map_point_title,
                        buttonText = R.string.add_map_point_title,
                        onCloseBottomSheet = { showBottomSheet.value = false },
                        onCheckButtonClicked = { onAddStep() }
                    )
                }
                BottomSheetType.EditStep -> {
                    MapPointSheetContent(
                        isEditing = true,
                        title = R.string.edit_map_point_title,
                        buttonText = R.string.save_changes,
                        onCloseBottomSheet = { showBottomSheet.value = false },
                        onCheckButtonClicked = { onEditMapPoint() }
                    )
                }
            }
        }
    }

    if (showDialog.value) {
        when(dialogType.value) {
            DialogType.Error -> { ErrorDialog(onRetry, onNavigateBack = { showDialog.value = false; onNavigateBack() }) }
            DialogType.DatePicker -> {
                ArrivalDatePickerDialog(
                    onDismiss = { showDialog.value = false },
                    dateConstraints = mapPointForm.dateConstraints,
                    onDateSelected = { arrivalDate -> onDateSelected(arrivalDate); showDialog.value = false }
                )
            }
            DialogType.TimePicker -> {
                ArrivalTimePickerDialog(
                    onDismiss = { showDialog.value = false },
                    onTimeSelected = { hour, min -> onTimeSelected(hour, min); showDialog.value = false }
                )
            }
            DialogType.ChooseLocation -> {
                LocationPickerDialog(
                    onConfirm = { lat, lng -> onUpdateCoordinates(lat, lng); showDialog.value = false },
                    onDismiss = { showDialog.value = false }
                )
            }
        }
    }
}