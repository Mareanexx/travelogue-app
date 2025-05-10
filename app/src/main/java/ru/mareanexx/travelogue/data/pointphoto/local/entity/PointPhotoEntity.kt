package ru.mareanexx.travelogue.data.pointphoto.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.mareanexx.travelogue.data.mappoint.local.entity.MapPointEntity

@Entity(
    tableName = "point_photo",
    foreignKeys = [ForeignKey(
        entity = MapPointEntity::class,
        parentColumns = ["id"],
        childColumns = ["map_point_id"],
        onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["map_point_id"])]
)
data class PointPhotoEntity(
    @PrimaryKey val id: Int,
    val filePath: String,
    @ColumnInfo(name = "map_point_id")
    val mapPointId: Int
)
