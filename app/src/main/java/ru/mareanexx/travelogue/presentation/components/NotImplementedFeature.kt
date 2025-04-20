package ru.mareanexx.travelogue.presentation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.mareanexx.travelogue.R

@Composable
fun NotImplementedFeature() {
    Toast.makeText(LocalContext.current, stringResource(R.string.not_implemented), Toast.LENGTH_SHORT).show()
}