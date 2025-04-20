package ru.mareanexx.travelogue.data.trip.remote.dto

import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagRequest
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import java.time.LocalDate

data class EditTripRequest(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate?,
    val type: TripVisibilityType? = null,
    val status: TripTimeStatus? = null,
    val tagList: List<NewTagRequest>? = null
)