package ru.mareanexx.feature_profiles.presentation.screens.profile.components.trips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.components.interactive.CustomOutlinedTextField
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.disabledButtonContainer
import ru.mareanexx.common.ui.theme.mainBorderColor
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.ui.theme.profilePrimaryText
import ru.mareanexx.feature_profiles.R

@Composable
fun AddTagButton(onAddTag: () -> Unit) {
    Button(
        modifier = Modifier.height(30.dp),
        contentPadding = PaddingValues(start = 7.dp, end = 12.dp),
        border = BorderStroke(width = 2.dp, color = mainBorderColor),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = mainBorderColor),
        onClick = onAddTag
    ) {
        Icon(modifier = Modifier.size(20.dp), painter = painterResource(ru.mareanexx.core.common.R.drawable.plus_icon), contentDescription = null)
        Text(text = stringResource(R.string.add_tag), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun ConcreteTag(tagName: String, onTagDelete: () -> Unit) {
    Row(
        modifier = Modifier.height(30.dp)
            .background(primaryText, Shapes.medium).padding(start = 7.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier.size(20.dp), painter = painterResource(R.drawable.tag_icon), contentDescription = null, tint = Color.White)
        Text(
            text = tagName, color = Color.White,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
        )
        Box(
            modifier = Modifier.padding(start = 8.dp)
                .background(Color.LightGray, CircleShape)
                .clickable { onTagDelete() }
        ) {
            Icon(
                modifier = Modifier.size(15.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun CreateTagDialog(
    enteredTagName: String,
    onTagNameChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    onAddTagClicked: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        titleContentColor = profilePrimaryText,
        onDismissRequest = onCancelClicked,
        title = { Text(text = stringResource(R.string.enter_tag_name_title), style = MaterialTheme.typography.displaySmall) },
        text = {
            CustomOutlinedTextField(
                textRes = R.string.tf_label_enter_tag_name,
                value = enteredTagName,
                onValueChanged = onTagNameChanged,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            )
        },
        dismissButton = {
            TextButton(onClick = onCancelClicked) {
                Text(
                    text = stringResource(ru.mareanexx.core.common.R.string.cancel_btn),
                    style = MaterialTheme.typography.labelSmall,
                    color = disabledButtonContainer
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onAddTagClicked(); onCancelClicked() }) {
                Text(
                    text = stringResource(R.string.add_tag),
                    color = primaryText,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    )
}