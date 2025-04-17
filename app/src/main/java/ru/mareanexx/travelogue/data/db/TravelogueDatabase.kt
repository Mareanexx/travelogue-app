package ru.mareanexx.travelogue.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.profile.local.entity.ProfileEntity

@Database(
    entities = [ProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TravelogueDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}