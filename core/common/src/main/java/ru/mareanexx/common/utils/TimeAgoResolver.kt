package ru.mareanexx.common.utils

import java.time.Duration
import java.time.OffsetDateTime

object TimeAgoResolver {
    fun resolveTimeAgo(createdAt: OffsetDateTime): String {
        val now = OffsetDateTime.now()
        val duration = Duration.between(createdAt, now)
        val seconds = duration.seconds

        return when {
            seconds < 0 -> "just now"
            seconds < 60 -> pluralize(seconds, "sec")
            seconds < 3600 -> pluralize(seconds / 60, "min")
            seconds < 86400 -> pluralize(seconds / 3600, "hour")
            seconds < 604800 -> pluralize(seconds / 86400, "day")
            seconds < 2592000 -> pluralize(seconds / 604800, "week")
            seconds < 31536000 -> pluralize(seconds / 2592000, "month")
            else -> pluralize(seconds / 31536000, "year")
        }
    }

    private fun pluralize(value: Long, unit: String): String {
        return if (value == 1L) "$value $unit ago" else "$value ${unit}s ago"
    }
}