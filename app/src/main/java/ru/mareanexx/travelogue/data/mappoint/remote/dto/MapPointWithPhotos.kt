package ru.mareanexx.travelogue.data.mappoint.remote.dto

import ru.mareanexx.travelogue.domain.mappoint.entity.MapPoint
import ru.mareanexx.travelogue.domain.mappoint.entity.PointPhoto

data class MapPointWithPhotos(
    val mapPoint: MapPoint,
    val photos: List<PointPhoto>
)