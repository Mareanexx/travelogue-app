package ru.mareanexx.feature_profiles.presentation.screens.profile.components.trips

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.common.ui.theme.MontserratFamily
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.profileSecondaryText
import ru.mareanexx.common.ui.theme.selectedVariant
import ru.mareanexx.common.ui.theme.unfocusedIndicator
import ru.mareanexx.data.trip.type.TripVisibilityType
import ru.mareanexx.feature_profiles.R

private data class TripType(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val type: TripVisibilityType,
    @StringRes val description: Int
)

private object TripTypeContent {
    val privateTripType = TripType(
        R.string.trip_visibility_only_me,
        ru.mareanexx.core.common.R.drawable.lock_icon,
        TripVisibilityType.Private,
        R.string.trip_visibility_only_me_desc
    )

    val publicTripType = TripType(
        R.string.trip_visibility_everyone,
        ru.mareanexx.core.common.R.drawable.public_icon,
        TripVisibilityType.Public,
        R.string.trip_visibility_everyone_desc
    )
}


@Composable
fun TripVisibilityTypeRadioGroup(
    onTripVisibilityTypeSelected: (TripVisibilityType) -> Unit
) {
    val radioOptions = listOf(TripTypeContent.privateTripType, TripTypeContent.publicTripType)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = Modifier.fillMaxWidth().selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        radioOptions.forEach { radioType ->
            Column(
                modifier = Modifier.fillMaxWidth().height(100.dp)
                    .selectable(
                        selected = (radioType == selectedOption),
                        onClick = {
                            onOptionSelected(radioType)
                            onTripVisibilityTypeSelected(radioType.type)
                        },
                        role = Role.RadioButton
                    )
                    .border(width = 2.dp, color = if (radioType == selectedOption) selectedVariant else unfocusedIndicator, shape = Shapes.medium)
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier.padding(bottom = 10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
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
                Text(
                    text = stringResource(radioType.description), fontFamily = MontserratFamily,
                    color = profileSecondaryText, fontWeight = FontWeight.SemiBold, fontSize = 11.sp
                )
            }
        }
    }
}