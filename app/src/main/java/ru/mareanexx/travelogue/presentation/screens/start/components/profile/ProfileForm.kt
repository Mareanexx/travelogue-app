package ru.mareanexx.travelogue.presentation.screens.start.components.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.components.CustomOutlinedTextField
import ru.mareanexx.travelogue.presentation.screens.start.components.SupportingText
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.CreateProfileViewModel
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.UiState
import ru.mareanexx.travelogue.utils.FileUtils.uriToFile

@Composable
fun ProfileForm(
    onSuccessfulProfileCreation: () -> Unit,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val formState = viewModel.formState.collectAsState()
    val profileUiState = viewModel.profileState.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()

    when(profileUiState.value) {
        UiState.Init -> {}
        UiState.Error -> {}
        is UiState.ShowToast -> {
            Toast.makeText(LocalContext.current, (profileUiState.value as UiState.ShowToast).message, Toast.LENGTH_SHORT).show()
        }
        UiState.Success -> { onSuccessfulProfileCreation() }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(bottom = 30.dp)
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
            uiState = profileUiState,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            supportingText = { SupportingText(R.string.username_requirement) }
        )

        CustomOutlinedTextField(
            textRes = R.string.fullname_tf_label,
            value = formState.value.fullname,
            onValueChanged = { viewModel.onFullnameChanged(it) },
            uiState = profileUiState,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            supportingText = { SupportingText(R.string.fullname_requirement) }
        )

        CustomOutlinedTextField(
            textRes = R.string.bio_tf_label,
            value = formState.value.bio,
            onValueChanged = { viewModel.onBioChanged(it) },
            uiState = profileUiState,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(30.dp))

        CheckFieldsButton(
            textRes = R.string.finish_button_text,
            enabled = formState.value.buttonEnabled,
            showLoading = loadingState.value,
            onClick = { viewModel.createProfile() }
        )
    }
}