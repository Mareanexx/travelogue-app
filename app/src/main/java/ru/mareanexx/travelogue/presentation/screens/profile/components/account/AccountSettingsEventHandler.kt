package ru.mareanexx.travelogue.presentation.screens.profile.components.account

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.components.AreYouSureDialog
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event.AccountEvent

@Composable
fun AccountSettingsEventHandler(
    eventFlow: Flow<AccountEvent>,
    onDeleteConfirmed: () -> Unit,
    navigateToStartScreen: () -> Unit,
    closeModalBottomSheet: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is AccountEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                AccountEvent.ShowDeleteDialog -> { showDialog = true }
                AccountEvent.ReturnToStart -> {
                    closeModalBottomSheet()
                    navigateToStartScreen()
                }
            }
        }
    }

    if (showDialog) {
        AreYouSureDialog(
            additional = R.string.account_variant_del,
            onCancelClicked = { showDialog = false },
            onDeleteClicked = {
                onDeleteConfirmed()
                showDialog = false
            },
        )
    }
}