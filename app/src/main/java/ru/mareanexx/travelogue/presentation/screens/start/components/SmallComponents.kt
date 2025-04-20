package ru.mareanexx.travelogue.presentation.screens.start.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.blueSmallText


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
fun SupportingText(@StringRes supportText: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp).padding(end = 5.dp),
            painter = painterResource(R.drawable.error_icon),
            contentDescription = null,
        )
        Text(text = stringResource(supportText))
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
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .padding(end = 12.dp),
                painter = painterResource(R.drawable.app_icon),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.app_name),
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