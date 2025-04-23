package ru.mareanexx.travelogue.presentation.screens.profile.components.modalsheet

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import ru.mareanexx.travelogue.presentation.components.CustomOutlinedTextField
import ru.mareanexx.travelogue.presentation.components.LeadingIcon
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.AddTagButton
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.CalendarBlock
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.ConcreteTag
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.CoverPhotoChooserBox
import ru.mareanexx.travelogue.presentation.screens.profile.components.trips.CoverPhotoImage
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.TripsViewModel
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event.ProfileEvent
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.state.ProfileUiState
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.followNotificationBack
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText
import ru.mareanexx.travelogue.presentation.theme.unfocusedIndicator
import ru.mareanexx.travelogue.utils.FileUtils.uriToFile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripSheetContent(
    isEditing: Boolean,
    @StringRes buttonText: Int,
    @StringRes titleText: Int,
    onAction: (TripsViewModel) -> Unit,
    viewModel: TripsViewModel = hiltViewModel()
) {

    val tripUiState by viewModel.uiState.collectAsState()
    val tripForm by viewModel.formState.collectAsState()
    val bottomSheetState = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(uri, context)
            viewModel.onCoverPhotoSelected(file)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            if (event is ProfileEvent.ShowToast) {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
            text = stringResource(titleText),
            style = MaterialTheme.typography.displaySmall,
            color = profilePrimaryText, textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                launcher.launch("image/*")
            }
        ) {
            if (tripForm.coverPhoto == null && tripForm.coverPhotoPath == null) {
                CoverPhotoChooserBox()
            } else {
                CoverPhotoImage(tripForm, isEditing)
            }
        }

        CalendarBlock()

        CustomOutlinedTextField(
            textRes = R.string.name_your_trip,
            value = tripForm.name,
            onValueChanged = { viewModel.onTripNameChanged(it) },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            leadingIcon = { LeadingIcon(R.drawable.label_icon) },
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomOutlinedTextField(
            textRes = R.string.add_a_short_description,
            value = tripForm.description,
            onValueChanged = { viewModel.onDescriptionChanged(it) },
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            leadingIcon = { LeadingIcon(R.drawable.notes_icon) },
        )
        Text(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
            text = stringResource(R.string.add_up_to_three_tags),
            style = MaterialTheme.typography.bodySmall, color = profileSecondaryText
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            item {
                AddTagButton { viewModel.addNewTag() }
            }
            itemsIndexed(tripForm.tagList) { index, value ->
                ConcreteTag(value,
                    onValueChanged = { newVal -> viewModel.onConcreteTagNameChanged(index, newVal) },
                    onTagDelete = { viewModel.onConcreteTagDelete(index) }
                )
            }
        }
        TripVisibilityChooserComponent(
            visibilityValue = if (tripForm.type == TripVisibilityType.Private) R.string.trip_visibility_only_me else R.string.trip_visibility_everyone,
            onOpenModalBottomSheet = { bottomSheetState.value = true }
        )
        CheckFieldsButton(
            enabled = tripForm.buttonEnabled,
            textRes = buttonText,
            showLoading = tripUiState == ProfileUiState.IsLoading
        ) { onAction(viewModel) }
        Spacer(modifier = Modifier.height(25.dp))
    }

    if (bottomSheetState.value) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = { bottomSheetState.value = false }
        ) {
            TripVisibilitySheetContent(onBottomSheetClose = { bottomSheetState.value = false })
        }
    }
}

@Composable
fun TripVisibilityChooserComponent(
    @StringRes visibilityValue: Int,
    onOpenModalBottomSheet: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 30.dp).border(width = 2.dp, unfocusedIndicator, Shapes.medium)
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onOpenModalBottomSheet() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(
                painter = painterResource(R.drawable.private_icon), contentDescription = null,
                tint = primaryText, modifier = Modifier.size(30.dp)
            )
            Text(
                text = stringResource(R.string.trip_visibility),
                style = MaterialTheme.typography.labelSmall, fontSize = 15.sp, color = profileSecondaryText
            )
        }
        Text(
            text = stringResource(visibilityValue), style = MaterialTheme.typography.labelSmall, fontSize = 15.sp,
            color = followNotificationBack,
            fontWeight = FontWeight.Bold
        )
    }
}