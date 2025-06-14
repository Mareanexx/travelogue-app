package ru.mareanexx.common.ui.components.interactive

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.disabledButtonContainer
import ru.mareanexx.common.ui.theme.disabledButtonContent
import ru.mareanexx.common.ui.theme.enabledButtonContainer


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