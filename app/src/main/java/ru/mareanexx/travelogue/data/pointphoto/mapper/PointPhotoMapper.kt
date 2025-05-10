package ru.mareanexx.travelogue.data.pointphoto.mapper

import ru.mareanexx.travelogue.data.pointphoto.local.entity.PointPhotoEntity
import ru.mareanexx.travelogue.data.pointphoto.remote.dto.DeletedPhoto
import ru.mareanexx.travelogue.domain.mappoint.entity.PointPhoto

fun PointPhotoEntity.toResponse() = PointPhoto(
    id = id,
    filePath = filePath,
    mapPointId = mapPointId
)

fun PointPhoto.toEntity() = PointPhotoEntity(
    id = id,
    filePath = filePath,
    mapPointId = mapPointId
)

fun PointPhoto.toDeleted() = DeletedPhoto(
    id = id,
    filePath = filePath
)