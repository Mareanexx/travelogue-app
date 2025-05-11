package ru.mareanexx.travelogue.domain.comments.entity

import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorCommentSender

data class CommentsWithAuthor(
    val author: AuthorCommentSender,
    val comments: List<Comment>
)