package ru.mareanexx.travelogue.presentation.screens.start.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.AuthUiState
import ru.mareanexx.travelogue.presentation.theme.blueSmallText
import ru.mareanexx.travelogue.presentation.theme.focusedTextField
import ru.mareanexx.travelogue.presentation.theme.textFieldText
import ru.mareanexx.travelogue.presentation.theme.unfocusedIndicatorTextField


@Composable
fun authTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        errorContainerColor = Color.White,
        unfocusedIndicatorColor = unfocusedIndicatorTextField,
        unfocusedLabelColor = textFieldText,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedIndicatorColor = focusedTextField,
        focusedLabelColor = focusedTextField
    )
}

@Composable
fun AuthOutlinedTextField(
    text: String, value: String,
    onValueChanged: (String) -> Unit,
    authState: State<AuthUiState>,
    imeAction: ImeAction, keyboardType: KeyboardType,
    visualTransformation: VisualTransformation? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        colors = authTextFieldColors(),
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChanged(it) },
        isError = authState.value == AuthUiState.Error,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        label = { Text(text = text) },
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(15.dp),
        supportingText = supportingText,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation ?: VisualTransformation.None
    )
}

@Composable
fun TrailingIconComponent(passwordVisible: MutableState<Boolean>) {
    Text(
        modifier = Modifier
            .padding(end = 15.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                passwordVisible.value = !passwordVisible.value
            },
        text =  if (!passwordVisible.value) "Show" else "Hide",
        style = MaterialTheme.typography.labelSmall,
        color = blueSmallText
    )
}