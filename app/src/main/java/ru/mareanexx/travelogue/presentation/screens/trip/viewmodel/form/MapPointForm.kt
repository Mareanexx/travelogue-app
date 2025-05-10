package ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form

import ru.mareanexx.travelogue.domain.mappoint.entity.PointPhoto
import java.io.File
import java.time.LocalDate
import java.time.OffsetTime

data class MapPointForm(
    val longitude: Double = Double.MIN_VALUE,
    val latitude: Double = Double.MIN_VALUE,
    val name: String = "",
    val description: String = "",
    val arrivalDate: LocalDate = LocalDate.now(),
    val arrivalTime: OffsetTime = OffsetTime.now(),
    val photos: List<File> = emptyList(),

    // for editing
    val id: Int = -1,
    val serverPhotos: List<PointPhoto> = emptyList(),
    val deletedPhotos: List<PointPhoto> = emptyList(),

    val dateConstraints: DateConstraints = DateConstraints(),

    val buttonEnabled: Boolean = false
)

data class DateConstraints(
    val upperBound: LocalDate? = null,
    val lowerBound: LocalDate = LocalDate.now()
)