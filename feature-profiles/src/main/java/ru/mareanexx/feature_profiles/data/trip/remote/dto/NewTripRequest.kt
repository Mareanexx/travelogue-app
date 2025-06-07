package ru.mareanexx.feature_profiles.data.trip.remote.dto

import ru.mareanexx.data.trip.type.TripTimeStatus
import ru.mareanexx.data.trip.type.TripVisibilityType
import ru.mareanexx.network.data.tag.remote.dto.NewTagRequest
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