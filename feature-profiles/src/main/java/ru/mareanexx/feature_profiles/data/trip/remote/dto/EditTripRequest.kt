package ru.mareanexx.feature_profiles.data.trip.remote.dto

import ru.mareanexx.data.trip.type.TripTimeStatus
import ru.mareanexx.data.trip.type.TripVisibilityType
import ru.mareanexx.network.data.tag.remote.dto.NewTagRequest
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