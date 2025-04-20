package ru.mareanexx.travelogue.data.tag.mapper

import ru.mareanexx.travelogue.data.tag.local.entity.TagEntity
import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagResponse
import ru.mareanexx.travelogue.domain.tag.entity.Tag

fun Tag.toEntity() = TagEntity(
    id = id,
    name = name,
    tripId = tripId
)

fun NewTagResponse.toEntity(tripId: Int) = TagEntity(
    id = id,
    name = name,
    tripId = tripId
)