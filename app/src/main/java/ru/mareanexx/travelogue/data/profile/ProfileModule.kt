package ru.mareanexx.travelogue.data.profile

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.profile.remote.api.ProfileApi
import ru.mareanexx.travelogue.data.profile.repository.ProfileRepositoryImpl
import ru.mareanexx.travelogue.data.tag.local.dao.TagDao
import ru.mareanexx.travelogue.data.trip.local.dao.TripDao
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        userSession: UserSessionManager,
        profileApi: ProfileApi,
        profileDao: ProfileDao,
        tripDao: TripDao,
        tagDao: TagDao
    ): ProfileRepository {
        return ProfileRepositoryImpl(userSession, profileApi, profileDao, tripDao, tagDao)
    }
}