package ru.mareanexx.feature_profiles.presentation.screens.profile.components.modalsheet

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.components.SupportingText
import ru.mareanexx.common.ui.components.interactive.CheckFieldsButton
import ru.mareanexx.common.ui.components.interactive.CustomOutlinedTextField
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.disabledButtonContainer
import ru.mareanexx.common.ui.theme.disabledButtonContent
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.ui.theme.profilePrimaryText
import ru.mareanexx.feature_profiles.R
import ru.mareanexx.feature_profiles.presentation.screens.profile.components.account.AccountSettingsEventHandler
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.AccountSettingsViewModel
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.state.AccountUiState


@Composable
fun AccountSettingsSheetContent(
    navigateToStartScreen: () -> Unit,
    closeModalBottomSheet: () -> Unit,
    viewModel: AccountSettingsViewModel = hiltViewModel()
) {
    val userEmail = viewModel.userEmail.collectAsState()
    val accountUiState = viewModel.uiState.collectAsState()
    val buttonEnabled = viewModel.buttonEnabled.collectAsState()

    AccountSettingsEventHandler(
        eventFlow = viewModel.eventFlow,
        onDeleteConfirmed = { viewModel.deleteAccount() },
        navigateToStartScreen = navigateToStartScreen,
        closeModalBottomSheet = closeModalBottomSheet
    )

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
            text = stringResource(R.string.account_settings),
            style = MaterialTheme.typography.displaySmall,
            color = profilePrimaryText, textAlign = TextAlign.Center
        )
        CustomOutlinedTextField(
            textRes = ru.mareanexx.core.common.R.string.email_tf_label,
            value = userEmail.value,
            onValueChanged = { viewModel.onEmailChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email,
            supportingText = { if(accountUiState.value is AccountUiState.Error) SupportingText(supportText = ru.mareanexx.core.common.R.string.unavailable_email) }
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
                onClick = { viewModel.onDeleteAccountClicked() }
            ) { Text(text = stringResource(R.string.delete_account), style = MaterialTheme.typography.labelMedium) }
        }

        CheckFieldsButton(
            textRes = ru.mareanexx.core.common.R.string.save_changes,
            enabled = buttonEnabled.value,
            showLoading = accountUiState.value == AccountUiState.IsLoading
        ) { viewModel.updateEmail() }
        Spacer(modifier = Modifier.height(30.dp))
    }
}