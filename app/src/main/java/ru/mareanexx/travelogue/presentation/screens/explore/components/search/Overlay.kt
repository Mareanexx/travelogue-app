package ru.mareanexx.travelogue.presentation.screens.explore.components.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.explore.SearchOverlayState
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.SearchViewModel
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.event.ExploreEvent
import ru.mareanexx.travelogue.presentation.theme.MontserratFamily
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.primaryText
import ru.mareanexx.travelogue.presentation.theme.searchBackground
import ru.mareanexx.travelogue.presentation.theme.searchText

@Composable
fun SearchEventHandler(eventFlow: SharedFlow<ExploreEvent>) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when(event) {
                is ExploreEvent.ShowToast -> { Toast.makeText(context, event.message, Toast.LENGTH_SHORT) }
            }
        }
    }
}

@Composable
fun SearchOverlay(
    state: SearchOverlayState,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    onClose: () -> Unit,
    onNavigateToOthersProfile: (Int) -> Unit,
    onNavigateToTrip: (tripId: Int, profileId: String, username: String, avatar: String) -> Unit,
    searchViewModel: SearchViewModel
) {
    val focusRequester = remember { FocusRequester() }
    val searchUiState by searchViewModel.uiState.collectAsState()

    val selectedTab = remember { mutableIntStateOf(0) }

    SearchEventHandler(searchViewModel.eventFlow)

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClose() }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.White)
                .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(
                Modifier.weight(0.9f).height(40.dp).background(searchBackground, Shapes.extraSmall).padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = stringResource(R.string.explore_search_description),
                    tint = searchText
                )
                BasicTextField(
                    modifier = Modifier.weight(0.9f).focusRequester(focusRequester),
                    value = state.query,
                    onValueChange = { onQueryChanged(it) },
                    singleLine = true,
                    textStyle = TextStyle(color = searchText, fontSize = 14.sp,
                        fontWeight = FontWeight.Medium, fontFamily = MontserratFamily, lineHeight = 14.sp),
                )
                Button(
                    modifier = Modifier.width(25.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = CircleShape,
                    onClick = onClearQuery
                ) {
                    Icon(
                        modifier = Modifier.height(20.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close)
                    )
                }
            }
            TextButton(onClick = onClose, contentPadding = PaddingValues(horizontal = 0.dp)) {
                Text(
                    text = stringResource(R.string.close),
                    style = MaterialTheme.typography.labelMedium,
                    color = primaryText
                )
            }
        }
        SearchResultsTabs(searchUiState, selectedTab, searchViewModel, onNavigateToTrip, onNavigateToOthersProfile)
    }
}