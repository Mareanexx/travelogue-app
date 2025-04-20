package ru.mareanexx.travelogue.data.profile.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mareanexx.travelogue.data.profile.local.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(profile: ProfileEntity)
}