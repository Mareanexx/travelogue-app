package ru.mareanexx.travelogue.data.tag.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.mareanexx.travelogue.data.tag.local.entity.TagEntity

@Dao
interface TagDao {
    @Insert
    suspend fun insertTags(tags: List<TagEntity>)

    @Query("SELECT * FROM tags WHERE trip_id = :tripId")
    suspend fun getTripTags(tripId: Int): List<TagEntity>

    @Query("DELETE FROM tags WHERE trip_id = :tripId")
    fun deleteAllByTripId(tripId: Int)
}