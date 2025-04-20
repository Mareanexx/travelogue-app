package ru.mareanexx.travelogue.data.trip.remote.dto

import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagRequest
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import java.time.LocalDate

data class NewTripRequest(
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    var profileId: Int = -1,
    val tagList: List<NewTagRequest>? = null
)