package ru.mareanexx.feature_profiles.presentation.screens.follows.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.common.ui.components.BoxSkeleton
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.core.common.R

@Composable
fun FollowsSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)
        .padding(horizontal = 15.dp).shimmer(shimmer).systemBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(painter = painterResource(R.drawable.arrow_back_icon), contentDescription = null)
            BoxSkeleton(modifier = Modifier.width(200.dp), height = 20)
            Icon(painter = painterResource(R.drawable.search_icon), contentDescription = null)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                BoxSkeleton(Modifier.width(15.dp), height = 20)
                Text(text = stringResource(R.string.followers), color = primaryText, fontWeight = FontWeight.Bold)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                BoxSkeleton(Modifier.width(15.dp), height = 20)
                Text(text = stringResource(R.string.followings), color = primaryText, fontWeight = FontWeight.Bold)
            }
        }
        (1..7).forEach { _ ->
            BoxSkeleton(Modifier.fillMaxWidth().padding(bottom = 15.dp), height = 60)
        }
    }
}