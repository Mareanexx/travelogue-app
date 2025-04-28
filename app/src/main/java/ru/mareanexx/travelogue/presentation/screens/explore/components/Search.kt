package ru.mareanexx.travelogue.presentation.screens.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.MontserratFamily
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.searchBackground
import ru.mareanexx.travelogue.presentation.theme.searchText

@Composable
fun SearchRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp, start = 15.dp, end = 15.dp)
            .background(searchBackground, Shapes.extraSmall).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.search_icon),
            contentDescription = stringResource(R.string.explore_search_description),
            tint = searchText
        )
        BasicTextField(
            value = stringResource(R.string.explore_search_description),
            onValueChange = { TODO("Реализовать поиск по trips и по users") },
            textStyle = TextStyle(color = searchText, fontSize = 13.sp,
                fontWeight = FontWeight.Medium, fontFamily = MontserratFamily, lineHeight = 14.sp),
            singleLine = true
        )
    }
}