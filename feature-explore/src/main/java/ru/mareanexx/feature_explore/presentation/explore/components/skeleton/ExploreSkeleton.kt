package ru.mareanexx.feature_explore.presentation.explore.components.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.common.ui.components.BoxSkeleton
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.feature_explore.R


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).shimmer(shimmer)
            .padding(horizontal = 15.dp).systemBarsPadding()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().background(Color.White).padding(top = 10.dp, bottom = 15.dp),
            text = stringResource(ru.mareanexx.core.common.R.string.explore),
            style = MaterialTheme.typography.titleMedium
        )
        BoxSkeleton(Modifier.fillMaxWidth(), height = 40, shape = Shapes.extraSmall)

        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = stringResource(R.string.trending_tags_label),
            style = MaterialTheme.typography.labelMedium, color = primaryText
        )

        LazyHorizontalGrid(
            modifier = Modifier.height(313.dp),
            contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp),
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(8) { BoxSkeleton(Modifier.width(130.dp), height = 130, shape = Shapes.small) }
        }

        Text(
            text = stringResource(R.string.trending_trips_label),
            style = MaterialTheme.typography.labelMedium, color = primaryText
        )
        LazyRow(
            contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(2) { BoxSkeleton(Modifier.width(255.dp), height = 320, shape = Shapes.small) }
        }
    }
}

@Composable
fun SearchLoading() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.searching_process),
            color = primaryText
        )
        CircularProgressIndicator(modifier = Modifier.size(25.dp), color = primaryText)
    }
}