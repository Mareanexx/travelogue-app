package ru.mareanexx.travelogue.presentation.screens.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton

@Composable
fun ErrorRetryBlock(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(horizontal = 15.dp).systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().background(Color.White)
                .padding(top = 10.dp, bottom = 15.dp),
            text = stringResource(R.string.explore),
            style = MaterialTheme.typography.titleMedium
        )
        CheckFieldsButton(
            textRes = R.string.retry_btn,
            onClick = onRetry
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.retry_description),
            style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
        )
    }
}