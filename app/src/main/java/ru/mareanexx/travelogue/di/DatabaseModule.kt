package ru.mareanexx.travelogue.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mareanexx.travelogue.data.db.TravelogueDatabase
import ru.mareanexx.travelogue.data.mappoint.local.dao.MapPointDao
import ru.mareanexx.travelogue.data.pointphoto.local.dao.PointPhotoDao
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.tag.local.dao.TagDao
import ru.mareanexx.travelogue.data.trip.local.dao.TripDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TravelogueDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TravelogueDatabase::class.java,
            name = "travelogue_db"
        ).build()
    }

    @Provides
    fun provideProfileDao(db: TravelogueDatabase): ProfileDao {
        return db.profileDao()
    }

    @Provides
    fun provideTripDao(db: TravelogueDatabase): TripDao {
        return db.tripDao()
    }

    @Provides
    fun provideTagDao(db: TravelogueDatabase): TagDao {
        return db.tagDao()
    }

    @Provides
    fun provideMapPointDao(db: TravelogueDatabase): MapPointDao {
        return db.mapPointDao()
    }

    @Provides
    fun providePointPhotoDao(db: TravelogueDatabase): PointPhotoDao {
        return db.pointPhotoDao()
    }
}