package ru.mareanexx.travelogue.presentation.screens.profile.components.modalsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.TripVisibilityTypeRadioGroup
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.TripsViewModel
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText

@Composable
fun TripVisibilitySheetContent(
    onBottomSheetClose: () -> Unit,
    viewModel: TripsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            text = stringResource(R.string.trip_visibility),
            style = MaterialTheme.typography.labelLarge,
            color = profilePrimaryText, textAlign = TextAlign.Center
        )

        TripVisibilityTypeRadioGroup { newType -> viewModel.onTripVisibilityType(newType) }

        CheckFieldsButton(
            textRes = R.string.save_changes
        ) { onBottomSheetClose() }
        Spacer(modifier = Modifier.height(15.dp))
    }
}