package ru.mareanexx.travelogue.data.comments.mapper

import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorCommentSender
import ru.mareanexx.travelogue.domain.comments.entity.Comment

fun NewCommentResponse.toEntity(authorData: AuthorCommentSender) = Comment(
    id = id,
    text = text,
    sendDate = sendDate,
    senderProfileId = senderId!!,
    username = authorData.username,
    avatar = authorData.avatar
)