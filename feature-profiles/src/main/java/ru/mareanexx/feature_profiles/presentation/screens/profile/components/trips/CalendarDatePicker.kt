package ru.mareanexx.feature_profiles.presentation.screens.profile.components.trips

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.theme.MontserratFamily
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.mainBorderColor
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.ui.theme.profileSecondaryText
import ru.mareanexx.common.ui.theme.unfocusedIndicator
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.TripsViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun CalendarBlock(viewModel: TripsViewModel = hiltViewModel()) {
    val formState by viewModel.formState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val dateTextFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

    Column(
        modifier = Modifier.padding(top = 12.dp, bottom = 10.dp)
            .border(color = unfocusedIndicator, width = 2.dp, shape = Shapes.medium)
            .clickable { showDialog.value = true }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(modifier = Modifier.size(25.dp), painter = painterResource(R.drawable.calendar_icon), contentDescription = null, tint = primaryText)
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.trip_dates),
                style = MaterialTheme.typography.labelSmall, fontSize = 15.sp, color = profileSecondaryText
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp, color = unfocusedIndicator)

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            OneDatePicker(R.string.start_date, formState.startDate.format(dateTextFormatter), Alignment.Start)
            Icon(modifier = Modifier.size(30.dp), painter = painterResource(R.drawable.arrow_forward_icon), contentDescription = null)
            OneDatePicker(R.string.end_date, formState.endDate?.format(dateTextFormatter) ?: "Optional", Alignment.End)
        }
    }

    if (showDialog.value) {
        DateRangePickerDialog(
            onDismiss = { showDialog.value = false },
            onDatesSelected = { startMillis, endMillis ->
                val startDate = Instant.ofEpochMilli(startMillis).atZone(ZoneId.systemDefault()).toLocalDate()
                val endDate = endMillis?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }
                viewModel.onDatesChanged(startDate, endDate)
                showDialog.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onDatesSelected: (start: Long, end: Long?) -> Unit
) {
    val state = rememberDateRangePickerState()

    DatePickerDialog(
        colors = DatePickerDefaults.colors(containerColor = Color.White),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val start = state.selectedStartDateMillis
                    val end = state.selectedEndDateMillis
                    if (start != null) onDatesSelected(start, end)
                }
            ) { Text(stringResource(ru.mareanexx.core.common.R.string.ok_btn), style = MaterialTheme.typography.labelMedium, color = primaryText) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(ru.mareanexx.core.common.R.string.cancel_btn), style = MaterialTheme.typography.labelMedium, color = primaryText) }
        }
    ) {
        DateRangePicker(
            colors = DatePickerDefaults.colors(containerColor = Color.White, selectedDayContainerColor = mainBorderColor),
            state = state,
            title = { Text(modifier = Modifier.padding(top = 10.dp, start = 20.dp), text = "Select trip dates", style = MaterialTheme.typography.titleSmall) },
            headline = null
        )
    }
}

@Composable
fun OneDatePicker(
    @StringRes dateType: Int,
    value: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(horizontalAlignment = horizontalAlignment) {
        Text(text = stringResource(dateType), style = MaterialTheme.typography.bodySmall, color = profileSecondaryText)
        Text(
            text = value, style = TextStyle(
                fontFamily = MontserratFamily, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold
            ),
            color = mainBorderColor
        )
    }
}