package ru.mareanexx.travelogue.domain.trip.entity

import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagResponse
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import java.time.LocalDate

data class Trip(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val stepsNumber: Int,
    val daysNumber: Int,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String,
    val profileId: Int,
    val tagList: List<NewTagResponse>?
)