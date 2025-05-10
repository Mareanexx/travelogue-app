package ru.mareanexx.travelogue.presentation.screens.trip.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form.DateConstraints
import ru.mareanexx.travelogue.presentation.theme.mainBorderColor
import ru.mareanexx.travelogue.presentation.theme.primaryText
import java.time.Instant
import java.time.ZoneOffset
import java.util.Calendar

@Composable
fun ErrorDialog(onRetry: () -> Unit, onNavigateBack: () -> Unit) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onNavigateBack,
        dismissButton = {
            TextButton(onClick = onNavigateBack) {
                Text(stringResource(R.string.back_to_prev_btn), style = MaterialTheme.typography.labelMedium, color = primaryText)
            }
        },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.retry_btn), style = MaterialTheme.typography.labelMedium, color = primaryText)
            }
        },
        title = { Text(stringResource(R.string.something_went_wrong), style = MaterialTheme.typography.titleSmall, color = primaryText) },
        text = { Text(stringResource(R.string.error_dialog_content), style = MaterialTheme.typography.labelMedium, color = primaryText) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrivalTimePickerDialog(onDismiss: () -> Unit, onTimeSelected: (hour: Int, min: Int) -> Unit) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_btn), style = MaterialTheme.typography.labelMedium, color = primaryText)
            }
        },
        confirmButton = {
            TextButton(onClick = { onTimeSelected(timePickerState.hour, timePickerState.minute) }) {
                Text(stringResource(R.string.ok_btn), style = MaterialTheme.typography.labelMedium, color = primaryText)
            }
        },
        text = {
            TimeInput(
                state = timePickerState,
                colors = TimePickerDefaults.colors(containerColor = Color.White)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrivalDatePickerDialog(
    dateConstraints: DateConstraints,
    onDismiss: () -> Unit,
    onDateSelected: (arrivalDate: Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneOffset.UTC).toLocalDate()
                val isAfterOrEqualLower = !date.isBefore(dateConstraints.lowerBound)
                val isBeforeOrEqualUpper = dateConstraints.upperBound?.let { !date.isAfter(it) } ?: true

                return isAfterOrEqualLower && isBeforeOrEqualUpper
            }
        }
    )

    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        colors = DatePickerDefaults.colors(containerColor = Color.White),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { datePickerState.selectedDateMillis?.let { onDateSelected(it) } },
                enabled = confirmEnabled
            ) { Text(stringResource(R.string.ok_btn), style = MaterialTheme.typography.labelMedium, color = primaryText) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel_btn), style = MaterialTheme.typography.labelMedium, color = primaryText) }
        }
    ) {
        DatePicker(
            colors = DatePickerDefaults.colors(containerColor = Color.White, selectedDayContainerColor = mainBorderColor),
            state = datePickerState,
            title = { Text(modifier = Modifier.padding(top = 10.dp, start = 20.dp), text = stringResource(R.string.select_arrival_date), style = MaterialTheme.typography.titleSmall) },
        )
    }
}