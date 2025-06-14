package ru.mareanexx.data.trip.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mareanexx.data.db.converter.LocalDateConverter
import ru.mareanexx.data.profile.entity.ProfileEntity
import ru.mareanexx.data.trip.type.TripTimeStatus
import ru.mareanexx.data.trip.type.TripVisibilityType
import java.time.LocalDate

@Entity(
    tableName = "trip",
    foreignKeys = [ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["profile_id"],
        onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["profile_id"])]
)
@TypeConverters(value = [LocalDateConverter::class])
data class TripEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val stepsNumber: Int,
    val daysNumber: Int,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String,

    @ColumnInfo(name = "profile_id")
    val profileId: Int
)