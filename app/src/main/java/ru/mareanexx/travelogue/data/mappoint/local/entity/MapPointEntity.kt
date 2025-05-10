package ru.mareanexx.travelogue.data.mappoint.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mareanexx.travelogue.data.db.converter.OffsetDateTimeConverter
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity
import java.time.OffsetDateTime

@Entity(
    tableName = "map_point",
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["trip_id"],
        onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["trip_id"])]
)
@TypeConverters(OffsetDateTimeConverter::class)
data class MapPointEntity(
    @PrimaryKey val id: Int,
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val likesNumber: Int = 0,
    val commentsNumber: Int = 0,
    val photosNumber: Int = 0,
    val arrivalDate: OffsetDateTime,

    @ColumnInfo(name = "trip_id")
    val tripId: Int
)