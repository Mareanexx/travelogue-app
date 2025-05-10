package ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.domain.comments.entity.Comment
import ru.mareanexx.travelogue.presentation.components.BoxSkeleton
import ru.mareanexx.travelogue.presentation.components.authTextFieldColors
import ru.mareanexx.travelogue.presentation.screens.start.components.CheckFieldsButton
import ru.mareanexx.travelogue.presentation.screens.trip.components.SendCommentButton
import ru.mareanexx.travelogue.presentation.theme.commentRowIndicator
import ru.mareanexx.travelogue.presentation.theme.mapPointsRowBack
import ru.mareanexx.travelogue.presentation.theme.profileSecondaryText
import ru.mareanexx.travelogue.utils.TimeAgoResolver

@Preview(showBackground = true)
@Composable
fun CommentsSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp).shimmer(shimmer), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        (1..3).forEach { _ ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                BoxSkeleton(Modifier.width(36.dp), height = 36, shape = CircleShape)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    BoxSkeleton(Modifier.width(60.dp), height = 10, shape = RoundedCornerShape(4.dp))
                    BoxSkeleton(Modifier.width(150.dp), height = 10, shape = RoundedCornerShape(4.dp))
                }
            }
        }
    }
}


@Composable
fun ErrorLoadingComments(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CheckFieldsButton(
            textRes = R.string.retry_btn,
            onClick = onRetry
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.cant_load_comments),
            style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OneCommentRow(comment: Comment) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(36.dp).clip(CircleShape),
            model = "${BuildConfig.API_FILES_URL}${comment.avatar}",
            contentDescription = stringResource(R.string.cd_avatar_photo),
            placeholder = painterResource(R.drawable.avatar_placeholder),
            error = painterResource(R.drawable.avatar_placeholder),
            contentScale = ContentScale.Crop,
        )

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = comment.username, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp))

                Text(
                    text = TimeAgoResolver.resolveTimeAgo(comment.sendDate),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black.copy(alpha = 0.4f), fontSize = 10.sp)
                )
            }
            Text(text = comment.text, style = MaterialTheme.typography.bodySmall.copy(color = profileSecondaryText, lineHeight = 16.sp))
        }
    }
}

@Composable
fun WriteACommentInputBlock(
    avatar: String,
    alignModifier: Modifier,
    commentText: State<String>,
    onCommentTextChanged: (String) -> Unit,
    onAddNewComment: () -> Unit
) {
    Row(
        modifier = alignModifier.fillMaxWidth().background(mapPointsRowBack)
            .padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(42.dp).clip(CircleShape),
            model = "${BuildConfig.API_FILES_URL}${avatar}",
            contentDescription = stringResource(R.string.cd_avatar_photo),
            placeholder = painterResource(R.drawable.avatar_placeholder),
            error = painterResource(R.drawable.avatar_placeholder),
            contentScale = ContentScale.Crop
        )
        OutlinedTextField(
            modifier = Modifier.weight(1f).height(48.dp),
            value = commentText.value,
            onValueChange = { onCommentTextChanged(it) },
            placeholder = { Text(text = stringResource(R.string.write_a_comment), style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp), color = profileSecondaryText) },
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp, color = profileSecondaryText),
            shape = RoundedCornerShape(15.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send, keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onSend = { onAddNewComment() }),
            maxLines = 2,
            colors = authTextFieldColors(commentRowIndicator, commentRowIndicator),
            trailingIcon = { SendCommentButton(onSendComment = { onAddNewComment() }) },
        )
    }
}