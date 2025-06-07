package ru.mareanexx.common.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SupportingText(@StringRes supportText: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(20.dp).padding(end = 5.dp),
            painter = painterResource(ru.mareanexx.core.common.R.drawable.error_icon),
            contentDescription = null,
        )
        Text(text = stringResource(supportText))
    }
}