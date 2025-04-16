package ru.mareanexx.travelogue.presentation.screens.start.components.login

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.start.components.AuthButton
import ru.mareanexx.travelogue.presentation.screens.start.components.AuthOutlinedTextField
import ru.mareanexx.travelogue.presentation.screens.start.components.ForgotPassword
import ru.mareanexx.travelogue.presentation.screens.start.components.SupportingText
import ru.mareanexx.travelogue.presentation.screens.start.components.TrailingIconComponent
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.LoginViewModel
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.AuthUiState
import ru.mareanexx.travelogue.presentation.theme.enabledButtonContainer


@Composable
fun LoginForm(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val formState = viewModel.formState.collectAsState()
    val loginState = viewModel.loginState.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    val passwordVisible = remember { mutableStateOf(false) }

    when(loginState.value) {
        is AuthUiState.Init -> {}
        is AuthUiState.ShowToast -> {
            Toast.makeText(LocalContext.current, (loginState.value as AuthUiState.ShowToast).message, Toast.LENGTH_SHORT).show()
        }
        is AuthUiState.Success -> { onLoginSuccess() }
        is AuthUiState.Error -> { viewModel.onCheckButtonEnable() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = stringResource(R.string.login_sheet_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        AuthOutlinedTextField(
            text = "Email",
            value = formState.value.email,
            onValueChanged = {
                viewModel.onEmailChanged(it)
                viewModel.onCheckButtonEnable()
            },
            authState = loginState,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        )

        AuthOutlinedTextField(
            text = "Password",
            value = formState.value.password,
            onValueChanged = {
                viewModel.onPasswordChanged(it)
                viewModel.onCheckButtonEnable()
            },
            authState = loginState,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
            trailingIcon = { TrailingIconComponent(passwordVisible) },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            supportingText = {
                if (loginState.value == AuthUiState.Error) {
                    SupportingText(R.string.incorrect_credentials)
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        AuthButton(
            containerColor = enabledButtonContainer,
            contentColor = Color.White,
            textRes = R.string.log_in_button,
            showLoading = loadingState.value,
            enabled = formState.value.enabledButton
        ) { viewModel.login() }
        ForgotPassword()
    }
}