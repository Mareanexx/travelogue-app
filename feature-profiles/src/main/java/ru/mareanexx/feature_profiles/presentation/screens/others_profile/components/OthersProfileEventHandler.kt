package ru.mareanexx.feature_profiles.presentation.screens.others_profile.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.feature_profiles.presentation.screens.others_profile.viewmodel.event.OthersProfileEvent

@Composable
fun OthersProfileEventHandler(eventFlow: SharedFlow<OthersProfileEvent>) {
    val notImplementedText = stringResource(ru.mareanexx.core.common.R.string.not_implemented)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                OthersProfileEvent.ShowNotImplementedToast -> {
                    Toast.makeText(context, notImplementedText, Toast.LENGTH_SHORT).show()
                }
                is OthersProfileEvent.ShowErrorToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}