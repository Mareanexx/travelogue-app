package ru.mareanexx.travelogue.presentation.screens.start.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContent
import ru.mareanexx.travelogue.presentation.theme.enabledButtonContainer


@Composable
fun CheckFieldsButton(
    containerColor: Color = enabledButtonContainer,
    contentColor: Color = Color.White,
    @StringRes textRes: Int,
    enabled: Boolean = true,
    showLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledButtonContainer,
            disabledContentColor = disabledButtonContent
        ),
        contentPadding = PaddingValues(vertical = 15.dp),
        shape = Shapes.medium,
        onClick = onClick
    ) {
        if (showLoading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
        }
        else {
            Text(
                text = stringResource(textRes),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


@Composable
fun ButtonsColumn(
    modifier: Modifier = Modifier,
    onOpenLoginPanel: () -> Unit,
    onOpenRegisterPanel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CheckFieldsButton(
            containerColor = enabledButtonContainer,
            contentColor = Color.White,
            R.string.create_account_button,
            onClick = onOpenRegisterPanel
        )
        CheckFieldsButton(
            containerColor = Color.White,
            contentColor = Color.Black,
            R.string.log_in_button,
            onClick = onOpenLoginPanel
        )
    }
}