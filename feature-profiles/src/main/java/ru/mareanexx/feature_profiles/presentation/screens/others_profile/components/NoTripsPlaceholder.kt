package ru.mareanexx.feature_profiles.presentation.screens.others_profile.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.disabledButtonContent
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.feature_profiles.R

@Composable
fun NoTripsPlaceholder(
    @DrawableRes image: Int? = null,
    @StringRes title: Int,
    @StringRes smallText: Int? = null
) {
    Column(
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 100.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        image?.let {
            Image(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 60.dp),
                painter = painterResource(it),
                contentDescription = stringResource(R.string.no_trips_yet),
                contentScale = ContentScale.FillWidth
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(title),
            color = primaryText, textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall
        )
        smallText?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(it), textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = disabledButtonContent
            )
        }
    }
}