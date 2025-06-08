package ru.mareanexx.feature_auth.presentation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.feature_auth.presentation.screens.event.AuthEvent

@Composable
fun AuthEventHandler(eventFlow: SharedFlow<AuthEvent>) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is AuthEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                }
            }
        }
    }
}