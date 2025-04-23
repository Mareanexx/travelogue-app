package ru.mareanexx.travelogue.presentation.screens.profile.components.modalsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.AccountSettingsViewModel
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.AccountUiState
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.components.CustomOutlinedTextField
import ru.mareanexx.travelogue.presentation.screens.start.components.SupportingText
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContainer
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContent
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText


@Composable
fun AccountSettingsSheetContent(
    navigateToStartScreen: () -> Unit,
    closeModalBottomSheet: () -> Unit,
    viewModel: AccountSettingsViewModel = hiltViewModel()
) {
    val userEmail = viewModel.userEmail.collectAsState()
    val accountUiState = viewModel.uiState.collectAsState()
    val buttonEnabled = viewModel.buttonEnabled.collectAsState()

    LaunchedEffect(accountUiState.value) {
        if (accountUiState.value == AccountUiState.ReturnToStart) {
            closeModalBottomSheet()
            navigateToStartScreen()
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
            text = stringResource(R.string.account_settings),
            style = MaterialTheme.typography.displaySmall,
            color = profilePrimaryText, textAlign = TextAlign.Center
        )
        CustomOutlinedTextField(
            textRes = R.string.email_tf_label,
            value = userEmail.value,
            onValueChanged = { viewModel.onEmailChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email,
            supportingText = { if(accountUiState.value is AccountUiState.ShowToast) SupportingText(supportText = R.string.unavailable_email) }
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(primaryText, Color.White),
                shape = Shapes.medium, modifier = Modifier.weight(0.5f),
                onClick = { viewModel.logOut() }
            ) { Text(text = stringResource(R.string.log_out), style = MaterialTheme.typography.labelMedium) }
            Button(
                colors = ButtonDefaults.buttonColors(disabledButtonContainer, disabledButtonContent),
                shape = Shapes.medium, modifier = Modifier.weight(0.5f),
                onClick = { viewModel.deleteAccount() }
            ) { Text(text = stringResource(R.string.delete_account), style = MaterialTheme.typography.labelMedium) }
        }

        CheckFieldsButton(
            textRes = R.string.save_changes,
            enabled = buttonEnabled.value,
            showLoading = accountUiState.value == AccountUiState.IsLoading
        ) { viewModel.updateEmail() }
        Spacer(modifier = Modifier.height(30.dp))
    }
}