package ru.mareanexx.travelogue.data.comments.remote.dto

import java.time.OffsetDateTime

data class NewCommentRequest(
    var senderProfileId: Int = -1,
    val mapPointId: Int,
    val text: String,
    val sendDate: OffsetDateTime
)