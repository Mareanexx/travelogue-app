package ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.components.interactive.CheckFieldsButton
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.profilePrimaryText
import ru.mareanexx.common.ui.theme.profileSecondaryText
import ru.mareanexx.common.ui.theme.selectedVariant
import ru.mareanexx.common.ui.theme.unfocusedIndicator
import ru.mareanexx.data.trip.type.TripTimeStatus
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.TripsViewModel

@Composable
fun TripTypeSheetContent(
    onChangeBottomSheetType: () -> Unit,
    viewModel: TripsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            text = stringResource(R.string.kind_of_trip_label),
            style = MaterialTheme.typography.labelLarge,
            color = profilePrimaryText, textAlign = TextAlign.Center
        )

        TripTypeRadioGroup { newStatus -> viewModel.onTripTimeStatusChanged(newStatus) }

        CheckFieldsButton(
            textRes = ru.mareanexx.core.common.R.string.continue_button_text
        ) { onChangeBottomSheetType() }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

private data class TripStatus(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val type: TripTimeStatus,
)

private object TripStatusContent {
    val currentTripStatus = TripStatus(R.string.current_travel,
        R.drawable.current_icon,
        TripTimeStatus.Current
    )

    val pastTripStatus = TripStatus(R.string.past_trip,
        R.drawable.past_icon,
        TripTimeStatus.Past
    )
}

@Composable
fun TripTypeRadioGroup(
    onTripTypeSelected: (TripTimeStatus) -> Unit
) {
    val radioOptions = listOf(TripStatusContent.currentTripStatus, TripStatusContent.pastTripStatus)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = Modifier.fillMaxWidth().selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        radioOptions.forEach { radioType ->
            Row(
                modifier = Modifier.fillMaxWidth().height(66.dp)
                    .selectable(
                        selected = (radioType == selectedOption),
                        onClick = {
                            onOptionSelected(radioType)
                            onTripTypeSelected(radioType.type)
                        },
                        role = Role.RadioButton
                    )
                    .border(width = 2.dp, color = if (radioType == selectedOption) selectedVariant else unfocusedIndicator, shape = Shapes.medium)
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(selectedColor = selectedVariant, unselectedColor = unfocusedIndicator),
                    selected = (radioType == selectedOption), onClick = null
                )
                Icon(
                    painter = painterResource(radioType.icon), contentDescription = null,
                    tint = profileSecondaryText
                )
                Text(
                    text = stringResource(radioType.label),
                    style = MaterialTheme.typography.labelMedium,
                    color = profileSecondaryText
                )
            }
        }
    }
}