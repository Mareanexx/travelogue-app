package ru.mareanexx.feature_profiles.domain.trip.entity

import ru.mareanexx.data.trip.type.TripTimeStatus
import ru.mareanexx.data.trip.type.TripVisibilityType
import ru.mareanexx.network.data.tag.remote.dto.NewTagResponse
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