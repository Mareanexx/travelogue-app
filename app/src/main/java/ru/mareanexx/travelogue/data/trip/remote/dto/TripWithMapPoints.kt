package ru.mareanexx.travelogue.data.trip.remote.dto

import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.domain.trip.entity.Trip

data class TripWithMapPoints(
    val trip: Trip,
    val mapPoints: List<MapPointWithPhotos>
)