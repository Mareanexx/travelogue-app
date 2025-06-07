package ru.mareanexx.network.data.comments.mapper

import ru.mareanexx.data.profile.entity.ProfileEntity
import ru.mareanexx.network.data.comments.remote.dto.AuthorCommentSender
import ru.mareanexx.network.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.network.domain.comments.entity.Comment

fun NewCommentResponse.toEntity(authorData: AuthorCommentSender) =
    Comment(
        id = id,
        text = text,
        sendDate = sendDate,
        senderProfileId = senderId!!,
        username = authorData.username,
        avatar = authorData.avatar
    )

fun ProfileEntity.toCommentSender() = AuthorCommentSender(
    id = id,
    username = username,
    avatar = avatar
)