package ru.mareanexx.network.data.comments.remote.dto

import java.time.OffsetDateTime

data class NewCommentResponse(
    val id: Int,
    val text: String,
    val sendDate: OffsetDateTime,
    var senderId: Int? = null
)