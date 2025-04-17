package ru.mareanexx.travelogue.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mareanexx.travelogue.data.db.TravelogueDatabase
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
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
}