package ru.mareanexx.data.tag.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.mareanexx.data.tag.dto.TagWithTripId
import ru.mareanexx.data.tag.entity.TagEntity

@Dao
interface TagDao {
    @Insert
    suspend fun insertTags(tags: List<TagEntity>)

    @Query("SELECT id, name, trip_id AS tripId FROM tags WHERE trip_id IN (:tripIds)")
    suspend fun getTagsForTrips(tripIds: List<Int>): List<TagWithTripId>

    @Query("DELETE FROM tags WHERE trip_id = :tripId")
    suspend fun deleteAllByTripId(tripId: Int)

    @Query("SELECT * FROM tags WHERE trip_id = :tripId")
    suspend fun getTagsByTripId(tripId: Int): List<TagEntity>
}