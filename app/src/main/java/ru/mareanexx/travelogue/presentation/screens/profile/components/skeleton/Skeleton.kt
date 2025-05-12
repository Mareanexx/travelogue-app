package ru.mareanexx.travelogue.presentation.screens.profile.components.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun ProfileContentSkeleton() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(modifier = Modifier.shimmer(shimmer)) {
        BoxSkeleton(Modifier.fillMaxWidth(), 170)
        Row(modifier = Modifier.padding(top = 15.dp, start = 25.dp)) {
            BoxSkeleton(Modifier.width(83.dp), 83, CircleShape)
            Column(
                modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 25.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BoxSkeleton(Modifier.width(135.dp), 18)
                BoxSkeleton(Modifier.width(220.dp), 16)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            BoxSkeleton(Modifier.width(40.dp), 35)
            (1..2).forEach { _ -> BoxSkeleton(Modifier.width(75.dp), 35) }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            (1..2).forEach { _ -> BoxSkeleton(Modifier.width(155.dp), 35, RoundedCornerShape(10.dp)) }
            BoxSkeleton(Modifier.width(35.dp), 35, RoundedCornerShape(10.dp))
        }
    }
}

@Composable
fun TripsContentSkeleton() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(modifier = Modifier.shimmer(shimmer).fillMaxWidth().padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        Box(modifier = Modifier.clip(Shapes.medium)) {
            BoxSkeleton(Modifier.fillMaxWidth(), height = 255)

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileScreenSkeleton() {
    ProfileContentSkeleton()
}