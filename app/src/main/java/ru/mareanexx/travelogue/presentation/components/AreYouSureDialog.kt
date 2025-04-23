package ru.mareanexx.travelogue.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText

@Composable
fun AreYouSureDialog(
    @StringRes additional: Int,
    onCancelClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        titleContentColor = profilePrimaryText,
        onDismissRequest = onCancelClicked,
        title = { Text(text = "${stringResource(R.string.are_you_sure_text)} ${stringResource(additional)}", textAlign = TextAlign.Center) },
        confirmButton = {
            TextButton(
                onClick = onCancelClicked
            ) {
                Text(
                    text = stringResource(R.string.cancel_btn),
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        text = { Text(text = stringResource(R.string.cant_revert_changes), color = Color.Gray, textAlign = TextAlign.Center) },
        icon = { Icon(Icons.Default.Info, contentDescription = "Warning icon") },
        dismissButton = {
            TextButton(
                onClick = onDeleteClicked
            ) { Text(
                text = stringResource(R.string.delete_btn),
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall
            ) }
        }
    )
}