package ru.mareanexx.network.domain.comments.entity

import ru.mareanexx.network.data.comments.remote.dto.AuthorCommentSender

data class CommentsWithAuthor(
    val author: AuthorCommentSender,
    val comments: List<Comment>
)