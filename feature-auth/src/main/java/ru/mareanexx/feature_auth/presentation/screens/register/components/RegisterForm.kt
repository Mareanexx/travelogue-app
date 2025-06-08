package ru.mareanexx.feature_auth.presentation.screens.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.components.SupportingText
import ru.mareanexx.common.ui.components.interactive.CheckFieldsButton
import ru.mareanexx.common.ui.components.interactive.CustomOutlinedTextField
import ru.mareanexx.common.ui.components.interactive.TrailingIcon
import ru.mareanexx.common.ui.state.AuthUiState
import ru.mareanexx.feature_auth.R
import ru.mareanexx.feature_auth.presentation.components.AuthEventHandler
import ru.mareanexx.feature_auth.presentation.screens.register.viewmodel.RegisterViewModel


@Composable
fun RegisterForm(
    onOpenProfileCreatePanel: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val formState = viewModel.formState.collectAsState()
    val registerState = viewModel.registerState.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    val passwordVisible = remember { mutableStateOf(false) }

    AuthEventHandler(viewModel.eventFlow)

    when(registerState.value) {
        is AuthUiState.Init -> {}
        is AuthUiState.Success -> { onOpenProfileCreatePanel() }
        is AuthUiState.Error -> { }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = stringResource(R.string.register_sheet_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.register_input_requirements),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomOutlinedTextField(
            textRes = ru.mareanexx.core.common.R.string.email_tf_label,
            value = formState.value.email,
            onValueChanged = { viewModel.onEmailChanged(it) },
            isError = registerState.value == AuthUiState.Error,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
            supportingText = {
                if (registerState.value == AuthUiState.Error) {
                    SupportingText(ru.mareanexx.core.common.R.string.unavailable_email)
                }
            }
        )

        CustomOutlinedTextField(
            textRes = R.string.password_tf_label,
            value = formState.value.password,
            onValueChanged = { viewModel.onPasswordChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
            trailingIcon = { TrailingIcon(passwordVisible) },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        )

        CheckersGrid(formState)

        CheckFieldsButton(
            textRes = ru.mareanexx.core.common.R.string.continue_button_text,
            showLoading = loadingState.value,
            enabled = formState.value.enabledButton
        ) { viewModel.register() }
    }
}