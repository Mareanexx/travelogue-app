package ru.mareanexx.common.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.mainBorderColor
import ru.mareanexx.common.ui.theme.selectedVariant
import ru.mareanexx.core.common.R

@Composable
fun StartFollowButton(onStartFollowClicked: () -> Unit) {
    Button(
        modifier = Modifier.height(30.dp),
        shape = Shapes.small,
        contentPadding = PaddingValues(horizontal = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = selectedVariant),
        border = BorderStroke(width = 2.dp, color = selectedVariant),
        onClick = onStartFollowClicked
    ) {
        Text(
            text = stringResource(R.string.follow_btn),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}

@Composable
fun UnfollowButton(onUnfollowClicked: () -> Unit) {
    Button(
        modifier = Modifier.height(30.dp),
        shape = Shapes.small,
        contentPadding = PaddingValues(horizontal = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = mainBorderColor, contentColor = Color.White),
        onClick = onUnfollowClicked
    ) {
        Text(
            text = stringResource(R.string.following_btn),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}