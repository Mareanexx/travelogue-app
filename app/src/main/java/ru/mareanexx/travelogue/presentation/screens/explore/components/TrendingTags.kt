package ru.mareanexx.travelogue.presentation.screens.explore.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.domain.explore.entity.TopTag
import ru.mareanexx.travelogue.presentation.theme.primaryText

object TagBackgroundsDB {
    val repository = listOf(
        R.drawable.tag_1,
        R.drawable.tag_2,
        R.drawable.tag_3,
        R.drawable.tag_4,
        R.drawable.tag_5,
        R.drawable.tag_6,
        R.drawable.tag_7,
        R.drawable.tag_8
    )
}

@Composable
fun TrendingTagsGrid(trendingTags: List<TopTag>) {
    val db = TagBackgroundsDB.repository

    Text(
        modifier = Modifier.padding(horizontal = 15.dp),
        text = stringResource(R.string.trending_tags_label),
        style = MaterialTheme.typography.labelMedium, color = primaryText
    )

    LazyHorizontalGrid(
        modifier = Modifier.height(313.dp),
        contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp, start = 15.dp, end = 15.dp),
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(trendingTags) { index, tag ->
            TrendingTagCard(db[index], tag.name)
        }
    }
}

@Composable
fun TrendingTagCard(@DrawableRes background: Int, tagName: String) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(130.dp),
            painter = painterResource(background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = "#$tagName", style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
    }
}