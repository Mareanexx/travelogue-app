package ru.mareanexx.feature_profiles.presentation.screens.trip.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.blueBackground
import ru.mareanexx.common.ui.theme.disabledButtonContainer
import ru.mareanexx.common.ui.theme.disabledButtonContent
import ru.mareanexx.common.ui.theme.enabledButtonContainer
import ru.mareanexx.common.ui.theme.selectedVariant
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.feature_profiles.presentation.screens.trip.viewmodel.form.DateConstraints

@Composable
fun AddFirstStepButton(@StringRes text: Int, enabled: Boolean = true, onAddFirstStep: () -> Unit) {
    NoMapPointsDivider()
    Button(
        enabled = enabled,
        onClick = onAddFirstStep,
        contentPadding = PaddingValues(horizontal = 40.dp),
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = enabledButtonContainer, contentColor = Color.White,
            disabledContainerColor = disabledButtonContainer, disabledContentColor = disabledButtonContent
        )
    ) { Text(text = stringResource(text), style = MaterialTheme.typography.labelMedium) }
    NoMapPointsDivider()
}

@Composable
fun SmallAddIconButton(addNewMapPoint: () -> Unit) {
    Box(
        modifier = Modifier.size(18.dp).background(blueBackground, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { addNewMapPoint() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(ru.mareanexx.core.common.R.drawable.plus_icon),
            contentDescription = null, tint = Color.White
        )
    }
}

@Composable
fun AddButtonWithDividers(
    tripDate: TripWithMapPoints,
    previousMapPoint: MapPointWithPhotos?,
    nextMapPoint: MapPointWithPhotos?,
    onSetDateConstraints: (DateConstraints) -> Unit,
    onAddStep: () -> Unit
) {
    HasMapPointsDivider()
    SmallAddIconButton(addNewMapPoint = {
        onSetDateConstraints(DateConstraints(
            lowerBound = if (previousMapPoint != null) previousMapPoint.mapPoint.arrivalDate.toLocalDate() else tripDate.trip.startDate,
            upperBound = if (nextMapPoint != null) nextMapPoint.mapPoint.arrivalDate.toLocalDate() else tripDate.trip.endDate
        ))
        onAddStep()
    })
    HasMapPointsDivider()
}

@Composable
fun EditMapPointButton(onOpenEditSheet: () -> Unit) {
    Button(
        modifier = Modifier.height(32.dp),
        shape = Shapes.medium,
        contentPadding = PaddingValues(horizontal = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = selectedVariant),
        border = BorderStroke(width = 2.dp, color = selectedVariant),
        onClick = onOpenEditSheet
    ) {
        Text(
            text = stringResource(R.string.edit_map_point_title),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun SendCommentButton(onSendComment: () -> Unit) {
    Button(
        shape = Shapes.small,
        modifier = Modifier.size(32.dp),
        contentPadding = PaddingValues(horizontal = 5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = disabledButtonContainer, contentColor = disabledButtonContent),
        onClick = onSendComment
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(R.drawable.send_icon),
            tint = disabledButtonContent, contentDescription = stringResource(R.string.send_comment_btn)
        )
    }
}