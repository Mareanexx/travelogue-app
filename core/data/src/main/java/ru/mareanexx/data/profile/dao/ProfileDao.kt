package ru.mareanexx.data.profile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mareanexx.data.profile.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(profile: ProfileEntity)

    @Query("""
        UPDATE profile 
        SET tripsNumber = :tripsNumber, followersNumber = :followersNumber, followingNumber = :followingNumber 
        WHERE id = :profileId
    """)
    suspend fun updateStatsOnly(profileId: Int, tripsNumber: Int, followersNumber: Int, followingNumber: Int)
}