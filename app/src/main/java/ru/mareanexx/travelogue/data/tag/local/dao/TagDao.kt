package ru.mareanexx.travelogue.data.tag.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.mareanexx.travelogue.data.tag.local.entity.TagEntity
import ru.mareanexx.travelogue.data.tag.remote.dto.TagWithTripId

@Dao
interface TagDao {
    @Insert
    suspend fun insertTags(tags: List<TagEntity>)

    @Query("SELECT id, name, trip_id AS tripId FROM tags WHERE trip_id IN (:tripIds)")
    suspend fun getTagsForTrips(tripIds: List<Int>): List<TagWithTripId>

    @Query("DELETE FROM tags WHERE trip_id = :tripId")
    suspend fun deleteAllByTripId(tripId: Int)
}