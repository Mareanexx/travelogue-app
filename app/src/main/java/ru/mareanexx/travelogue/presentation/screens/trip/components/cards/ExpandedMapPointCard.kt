package ru.mareanexx.travelogue.presentation.screens.trip.components.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components.CommentsSkeleton
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components.DescriptionAndStatsBlock
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components.ErrorLoadingComments
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components.ExpandedMapPointCardHeader
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components.OneCommentRow
import ru.mareanexx.travelogue.presentation.screens.trip.components.cards.components.WriteACommentInputBlock
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.CommentsViewModel
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state.CommentsUiState
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.primaryText
import java.time.format.DateTimeFormatter
import java.util.Locale

val dateTextFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
val timeTextFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
fun ExpandedMapPointCard(
    profileId: String, username: String, avatar: String,
    mapPointData: MutableState<MapPointWithPhotos?>,
    onDismiss: () -> Unit, onOpenEditSheet: () -> Unit,
    commentsViewModel: CommentsViewModel = hiltViewModel(),
    onAddLike: (Int) -> Unit, onRemoveLike: (Int) -> Unit
) {
    val commentsData = commentsViewModel.commentsData.collectAsState()
    val commentsUiState = commentsViewModel.uiState.collectAsState()
    val commentMessage = commentsViewModel.commentMessage.collectAsState()

    LaunchedEffect(Unit) { commentsViewModel.loadComments(mapPointData.value!!.mapPoint.id) }

    val isDragged = remember { mutableStateOf(false) }
    val offsetY = remember { mutableFloatStateOf(0f) }

    val animatedHeight by animateDpAsState(
        targetValue = if (isDragged.value) 760.dp else 135.dp,
        animationSpec = tween(durationMillis = 400), label = "height_anim"
    )

    val imageHeight by animateDpAsState(
        targetValue = if (isDragged.value) 412.dp else 135.dp,
        animationSpec = tween(durationMillis = 400), label = "image_height_anim"
    )

    val alphaFloat by animateFloatAsState(
        targetValue = if (isDragged.value) 1f else 0f,
        animationSpec = tween(durationMillis = 350), label = "alpha_header_anim"
    )

    val imageWidth by animateDpAsState(
        targetValue = if (isDragged.value) 412.dp else 212.dp,
        animationSpec = tween(durationMillis = 400), label = "image_width_anim"
    )

    LaunchedEffect(Unit) { isDragged.value = true }

    val pagerState = rememberPagerState(pageCount = { mapPointData.value?.photos?.size ?: 0 })

    Column(
        modifier = Modifier.fillMaxWidth().height(animatedHeight).zIndex(2f)
            .offset { IntOffset(0, offsetY.floatValue.toInt()) }
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        offsetY.floatValue += dragAmount
                        isDragged.value = false
                    },
                    onDragEnd = {
                        if (offsetY.floatValue > 90f) {
                            isDragged.value = false
                            onDismiss()
                        } else {
                            offsetY.floatValue = 0f
                        }
                    }
                )
            }
            .background(Color.White).animateContentSize()
    ) {

        ExpandedMapPointCardHeader(alphaFloat, mapPointData)

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                    HorizontalPager(state = pagerState) { pageNumber ->
                        AsyncImage(
                            model = "${BuildConfig.API_FILES_URL}${mapPointData.value?.photos?.getOrNull(pageNumber)?.filePath}",
                            placeholder = painterResource(R.drawable.cover_placeholder),
                            error = painterResource(R.drawable.cover_placeholder),
                            contentDescription = null,
                            modifier = Modifier.width(imageWidth).height(imageHeight),
                            contentScale = ContentScale.Crop
                        )
                    }
                    if (mapPointData.value!!.photos.isNotEmpty()) {
                        Row(
                            modifier = Modifier.padding(bottom = 15.dp).background(primaryText, Shapes.medium)
                                .padding(vertical = 2.dp, horizontal = 4.dp)
                        ) {
                            repeat(pagerState.pageCount) { iteration ->
                                val color = if (pagerState.currentPage == iteration) Color.White else Color.White.copy(0.5f)
                                Box(modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(7.dp))
                            }
                        }
                    }
                }
            }
            item {
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth().padding(15.dp),
                    visible = isDragged.value
                ) {
                    DescriptionAndStatsBlock(mapPointData, onOpenEditSheet = { onOpenEditSheet(); onDismiss() },
                        onAddLike = {
                            onAddLike(mapPointData.value!!.mapPoint.id)
                            mapPointData.value = mapPointData.value!!.copy(mapPoint = mapPointData.value!!.mapPoint.copy(isLiked = true))
                        },
                        onRemoveLike = {
                            onRemoveLike(mapPointData.value!!.mapPoint.id)
                            mapPointData.value = mapPointData.value!!.copy(mapPoint = mapPointData.value!!.mapPoint.copy(isLiked = false))
                        }
                    )
                }
            }
            when(commentsUiState.value) {
                CommentsUiState.Init -> {}
                CommentsUiState.Error -> { item { ErrorLoadingComments(onRetry = { commentsViewModel.retry() }) } }
                CommentsUiState.Loading -> { item { CommentsSkeleton() } }
                CommentsUiState.Success -> {
                    items(commentsData.value) { comment ->
                        OneCommentRow(comment)
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }

        WriteACommentInputBlock(
            avatar,
            alignModifier = Modifier.align(Alignment.End),
            commentText = commentMessage,
            onCommentTextChanged = { newVal -> commentsViewModel.onCommentTextChanged(newVal) },
            onAddNewComment = { commentsViewModel.addNewComment() }
        )
    }
}