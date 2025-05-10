package ru.mareanexx.travelogue.data.comments.mapper

import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.travelogue.domain.comments.entity.Comment

fun NewCommentResponse.toEntity(username: String, avatar: String?) = Comment(
    id = id,
    text = text,
    sendDate = sendDate,
    senderProfileId = senderId!!,
    username = username,
    avatar = avatar
)