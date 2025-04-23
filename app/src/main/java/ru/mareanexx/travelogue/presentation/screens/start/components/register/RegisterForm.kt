package ru.mareanexx.travelogue.presentation.screens.start.components.register

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.components.CustomOutlinedTextField
import ru.mareanexx.travelogue.presentation.screens.start.components.SupportingText
import ru.mareanexx.travelogue.presentation.components.TrailingIcon
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.RegisterViewModel
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.UiState


@Composable
fun RegisterForm(
    onOpenProfileCreatePanel: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val formState = viewModel.formState.collectAsState()
    val registerState = viewModel.registerState.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    val passwordVisible = remember { mutableStateOf(false) }

    when(registerState.value) {
        is UiState.Init -> {}
        is UiState.ShowToast -> {
            Toast.makeText(LocalContext.current, (registerState.value as UiState.ShowToast).message, Toast.LENGTH_SHORT).show()
        }
        is UiState.Success -> { onOpenProfileCreatePanel() }
        is UiState.Error -> { }
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
            textRes = R.string.email_tf_label,
            value = formState.value.email,
            onValueChanged = { viewModel.onEmailChanged(it) },
            uiState = registerState,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        )

        CustomOutlinedTextField(
            textRes = R.string.password_tf_label,
            value = formState.value.password,
            onValueChanged = { viewModel.onPasswordChanged(it) },
            uiState = registerState,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
            trailingIcon = { TrailingIcon(passwordVisible) },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            supportingText = {
                if (registerState.value == UiState.Error) {
                    SupportingText(R.string.unavailable_email)
                }
            }
        )

        CheckersGrid(formState)

        CheckFieldsButton(
            textRes = R.string.continue_button_text,
            showLoading = loadingState.value,
            enabled = formState.value.enabledButton
        ) { viewModel.register() }
    }
}