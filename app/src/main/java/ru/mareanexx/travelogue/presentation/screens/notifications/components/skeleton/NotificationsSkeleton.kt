package ru.mareanexx.travelogue.presentation.screens.notifications.components.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.travelogue.presentation.components.BoxSkeleton
import ru.mareanexx.travelogue.presentation.theme.Shapes

@Composable
fun NotificationsScreenSkeleton() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp).shimmer(shimmer), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            BoxSkeleton(modifier = Modifier.width(34.dp), height = 34, shape = Shapes.small)
        }
        (1..6).forEach { _ ->
            BoxSkeleton(modifier = Modifier.fillMaxWidth(), height = 70, shape = Shapes.small)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNotificationsSkeleton() {
    NotificationsScreenSkeleton()
}