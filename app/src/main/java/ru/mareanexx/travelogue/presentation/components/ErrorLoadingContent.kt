package ru.mareanexx.travelogue.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContent

@Composable
fun ErrorLoadingContent(@StringRes message: Int, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
            painter = painterResource(R.drawable.error_loading_content_placeholder),
            contentDescription = stringResource(R.string.error_loading_others_profile),
            contentScale = ContentScale.FillWidth
        )
        CheckFieldsButton(
            textRes = R.string.retry_btn,
            onClick = onRetry
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            text = stringResource(message), color = disabledButtonContent,
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp), textAlign = TextAlign.Center
        )
    }
}