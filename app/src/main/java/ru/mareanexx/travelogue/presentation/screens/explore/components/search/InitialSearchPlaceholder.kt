package ru.mareanexx.travelogue.presentation.screens.explore.components.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.disabledButtonContent
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun InitialSearchPlaceholder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Image(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
                painter = painterResource(R.drawable.search_placeholder),
                contentDescription = stringResource(R.string.search_bar_is_empty_img),
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.search_empty_title),
                textAlign = TextAlign.Center, color = primaryText,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                text = stringResource(R.string.search_empty_description), color = disabledButtonContent,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp), textAlign = TextAlign.Center
            )
        }
    }
}