package ru.mareanexx.common.ui.components.interactive

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.state.UiState
import ru.mareanexx.common.ui.theme.blueSmallText
import ru.mareanexx.common.ui.theme.focusedTextField
import ru.mareanexx.common.ui.theme.textFieldText
import ru.mareanexx.common.ui.theme.unfocusedIndicator


@Composable
fun authTextFieldColors(
    unfocusedIndicatorColor: Color = unfocusedIndicator,
    focusedIndicatorColor: Color = focusedTextField
): TextFieldColors {
    return TextFieldDefaults.colors(
        errorContainerColor = Color.White,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
        unfocusedLabelColor = textFieldText,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedIndicatorColor = focusedIndicatorColor,
        focusedLabelColor = focusedTextField
    )
}

@Composable
fun CustomOutlinedTextField(
    @StringRes textRes: Int, value: String,
    onValueChanged: (String) -> Unit,
    uiState: State<UiState>? = null,
    imeAction: ImeAction, keyboardType: KeyboardType,
    visualTransformation: VisualTransformation? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        colors = authTextFieldColors(),
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChanged(it) },
        isError = if(uiState == null) false else uiState.value == UiState.Error,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        label = { Text(text = stringResource(textRes)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(15.dp),
        supportingText = supportingText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation ?: VisualTransformation.None
    )
}

@Composable
fun TrailingIcon(passwordVisible: MutableState<Boolean>) {
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

@Composable
fun LeadingIcon(@DrawableRes iconRes: Int) {
    Icon(
        modifier = Modifier.padding(start = 5.dp).size(25.dp),
        painter = painterResource(iconRes),
        contentDescription = null
    )
}