package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.form

import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import java.io.File
import java.time.LocalDate

data class TripForm(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = null,
    val type: TripVisibilityType = TripVisibilityType.Private,
    val status: TripTimeStatus = TripTimeStatus.Current,
    val coverPhoto: File? = null,
    val coverPhotoPath: String? = null,

    val newTagName: String = "",
    val tagList: List<String> = emptyList(),

    val buttonEnabled: Boolean = false
)