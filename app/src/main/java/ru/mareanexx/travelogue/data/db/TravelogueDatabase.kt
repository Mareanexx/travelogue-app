package ru.mareanexx.travelogue.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.profile.local.entity.ProfileEntity
import ru.mareanexx.travelogue.data.tag.local.dao.TagDao
import ru.mareanexx.travelogue.data.tag.local.entity.TagEntity
import ru.mareanexx.travelogue.data.trip.local.dao.TripDao
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity

@Database(
    entities = [ProfileEntity::class, TripEntity::class, TagEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TravelogueDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun tripDao(): TripDao
    abstract fun tagDao(): TagDao
}