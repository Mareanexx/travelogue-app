package ru.mareanexx.travelogue.presentation.screens.trip.components.modalsheet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.components.CustomOutlinedTextField
import ru.mareanexx.travelogue.presentation.components.LeadingIcon
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.screens.trip.components.ArrivalDateTimePicker
import ru.mareanexx.travelogue.presentation.screens.trip.components.DeleteMapPointButton
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.TripViewModel
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.DialogType
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state.MapPointUiState
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MapPointSheetContent(
    isEditing: Boolean = false,
    @StringRes title: Int,
    @StringRes buttonText: Int,
    onCloseBottomSheet: () -> Unit,
    onCheckButtonClicked: () -> Unit,
    viewModel: TripViewModel = hiltViewModel(),
) {
    val mapPointUiState = viewModel.mapPointUiState.collectAsState()
    val mapPointForm = viewModel.mapPointForm.collectAsState()

    val dateTextFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
    val timeTextFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
            text = stringResource(title),
            style = MaterialTheme.typography.displaySmall,
            color = profilePrimaryText, textAlign = TextAlign.Center
        )

        LocationBlock(
            mapPointForm = mapPointForm,
            onOpenChooseLocationDialog = { viewModel.showTypifiedDialog(DialogType.ChooseLocation) }
        )

        CustomOutlinedTextField(
            textRes = R.string.step_name_tf_label,
            value = mapPointForm.value.name,
            onValueChanged = { viewModel.onMapPointNameChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            leadingIcon = { LeadingIcon(R.drawable.label_icon) },
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ArrivalDateTimePicker(
                Modifier.weight(0.5f), R.drawable.calendar_icon, R.string.date_picker_cd,
                pickerType = R.string.arrival_date_tf_label,
                value = mapPointForm.value.arrivalDate.format(dateTextFormatter),
                onOpenPickerDialog = { viewModel.showTypifiedDialog(DialogType.DatePicker) }
            )
            ArrivalDateTimePicker(
                Modifier.weight(0.5f), R.drawable.time_icon, R.string.time_picker_cd,
                pickerType = R.string.arrival_time_tf_label,
                value = mapPointForm.value.arrivalTime.format(timeTextFormatter),
                onOpenPickerDialog = { viewModel.showTypifiedDialog(DialogType.TimePicker) }
            )
        }

        PointPhotosRow(
            mapPointForm = mapPointForm.value,
            addPhoto = { photo -> viewModel.onAddPhoto(photo) },
            deletePhoto = { photo -> viewModel.onRemovePhoto(photo) },
            onDeleteAndAddToDeleted = { photo -> viewModel.onRemovePhotoAndAddToDeletedList(photo) }
        )

        CustomOutlinedTextField(
            textRes = R.string.what_have_you_been_up_to_tf_label,
            value = mapPointForm.value.description,
            onValueChanged = { viewModel.onMapPointDescriptionChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            leadingIcon = { LeadingIcon(R.drawable.notes_icon) },
        )
        if (isEditing) {
            Spacer(Modifier.height(10.dp))
            DeleteMapPointButton(onDeleteMapPoint = { viewModel.deleteMapPoint(mapPointForm.value.id); onCloseBottomSheet() })
        }
        Spacer(modifier = Modifier.height(25.dp))
        CheckFieldsButton(
            enabled = mapPointForm.value.buttonEnabled,
            textRes = buttonText,
            showLoading = mapPointUiState.value is MapPointUiState.Loading,
            onClick = { onCheckButtonClicked(); onCloseBottomSheet() }
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}