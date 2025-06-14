package ru.mareanexx.feature_auth.presentation.screens.create_profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.common.ui.components.SupportingText
import ru.mareanexx.common.ui.components.interactive.CheckFieldsButton
import ru.mareanexx.common.ui.components.interactive.CustomOutlinedTextField
import ru.mareanexx.common.ui.state.AuthUiState
import ru.mareanexx.common.utils.FileUtils.uriToFile
import ru.mareanexx.feature_auth.R
import ru.mareanexx.feature_auth.presentation.components.AuthEventHandler
import ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.CreateProfileViewModel
import ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form.ProfileField

@Composable
fun ProfileForm(
    onSuccessfulProfileCreation: () -> Unit,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val formState = viewModel.formState.collectAsState()
    val profileUiState = viewModel.profileState.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()

    val usernameError = formState.value.fieldsError[ProfileField.Username]
    val fullnameError = formState.value.fieldsError[ProfileField.Fullname]

    AuthEventHandler(viewModel.eventFlow)

    when(profileUiState.value) {
        AuthUiState.Init -> {}
        AuthUiState.Error -> {}
        AuthUiState.Success -> { onSuccessfulProfileCreation() }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(uri, context)
            viewModel.onAvatarSelected(file)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 15.dp).padding(bottom = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.create_profile_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        ProfileAvatarRow(launcher, formState)

        CustomOutlinedTextField(
            textRes = R.string.username_tf_label,
            value = formState.value.username,
            onValueChanged = { viewModel.onUsernameChanged(it) },
            isError = usernameError != null,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            supportingText = { usernameError?.let { SupportingText(it.errorText) } }
        )

        CustomOutlinedTextField(
            textRes = R.string.fullname_tf_label,
            value = formState.value.fullname,
            onValueChanged = { viewModel.onFullnameChanged(it) },
            isError = fullnameError != null,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            supportingText = { fullnameError?.let { SupportingText(it.errorText) } }
        )

        CustomOutlinedTextField(
            textRes = R.string.bio_tf_label,
            value = formState.value.bio,
            onValueChanged = { viewModel.onBioChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(30.dp))

        CheckFieldsButton(
            textRes = R.string.finish_button_text,
            showLoading = loadingState.value,
            onClick = { viewModel.createProfile() }
        )
    }
}