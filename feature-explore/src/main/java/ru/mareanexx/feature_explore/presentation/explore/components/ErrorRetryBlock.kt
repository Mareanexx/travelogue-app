package ru.mareanexx.feature_explore.presentation.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.common.ui.components.interactive.CheckFieldsButton
import ru.mareanexx.common.ui.theme.disabledButtonContent
import ru.mareanexx.core.common.R


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
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {
                Image(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
                    painter = painterResource(R.drawable.error_loading_content_placeholder),
                    contentDescription = stringResource(R.string.error_loading_others_profile),
                    contentScale = ContentScale.FillWidth
                )
                CheckFieldsButton(textRes = R.string.retry_btn, onClick = onRetry)
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                    text = stringResource(R.string.retry_description), color = disabledButtonContent,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp), textAlign = TextAlign.Center
                )
            }
        }
    }
}