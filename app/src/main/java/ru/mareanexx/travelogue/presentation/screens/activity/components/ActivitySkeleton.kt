package ru.mareanexx.travelogue.presentation.screens.activity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.travelogue.presentation.components.BoxSkeleton
import ru.mareanexx.travelogue.presentation.theme.Shapes

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActivitySkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Column(
        modifier = Modifier.shimmer(shimmer).padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        (1..3).forEach { _ ->
            Box(modifier = Modifier.clip(Shapes.medium)) {
                BoxSkeleton(Modifier.fillMaxWidth(), height = 255)
                Row(
                    modifier = Modifier.background(Color.Gray).fillMaxWidth().height(70.dp).padding(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.background(Color.DarkGray, shape = CircleShape).size(44.dp))
                    Box(Modifier.background(Color.DarkGray, shape = Shapes.extraSmall).size(height = 14.dp, width = 90.dp))
                }

                Box(modifier = Modifier.align(Alignment.CenterStart).padding(start = 15.dp, top = 100.dp).background(
                    Color.Gray, shape = Shapes.extraSmall).size(height = 20.dp, width = 190.dp))
                Row(
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (1..4).forEach { _ ->
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.background(Color.Gray, shape = Shapes.extraSmall).size(height = 15.dp, width = 30.dp))
                            Box(modifier = Modifier.background(Color.Gray, shape = Shapes.extraSmall).size(height = 10.dp, width = 60.dp))
                        }
                    }
                }
            }
        }
    }
}