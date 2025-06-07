package ru.mareanexx.network.domain.comments.entity

import java.time.OffsetDateTime

data class Comment(
    val id: Int,
    val text: String,
    val sendDate: OffsetDateTime,
    val senderProfileId: Int,
    val username: String,
    val avatar: String?
)