package ru.mareanexx.travelogue.presentation.screens.follows.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.theme.profilePrimaryText


@Composable
fun ErrorRetry(profileUsername: String, navigateBack: () -> Unit, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(horizontal = 15.dp).systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { navigateBack() },
                painter = painterResource(R.drawable.arrow_back_icon),
                contentDescription = stringResource(R.string.get_back_cd)
            )
            Text(
                text = profileUsername,
                style = MaterialTheme.typography.titleSmall,
                color = profilePrimaryText
            )
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = stringResource(R.string.search_follows_cd)
            )
        }
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