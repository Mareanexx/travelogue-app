package ru.mareanexx.feature_auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.components.interactive.CheckFieldsButton
import ru.mareanexx.common.ui.theme.blueSmallText
import ru.mareanexx.common.ui.theme.enabledButtonContainer
import ru.mareanexx.feature_auth.R

@Composable
fun ButtonsColumn(
    modifier: Modifier = Modifier,
    onOpenLoginPanel: () -> Unit,
    onOpenRegisterPanel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CheckFieldsButton(
            containerColor = enabledButtonContainer,
            contentColor = Color.White,
            R.string.create_account_button,
            onClick = onOpenRegisterPanel
        )
        CheckFieldsButton(
            containerColor = Color.White,
            contentColor = Color.Black,
            R.string.log_in_button,
            onClick = onOpenLoginPanel
        )
    }
}

@Composable
fun ForgotPassword() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.labelSmall,
            color = blueSmallText
        )
    }
}

@Composable
fun LogoAndSloganTextColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(ru.mareanexx.core.common.R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        }
        Text(
            modifier = Modifier.width(350.dp),
            text = stringResource(R.string.app_slogan),
            style = MaterialTheme.typography.titleLarge
        )
    }
}