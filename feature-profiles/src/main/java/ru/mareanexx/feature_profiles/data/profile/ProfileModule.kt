package ru.mareanexx.feature_profiles.data.profile

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_profiles.data.profile.remote.api.ProfileApi
import ru.mareanexx.feature_profiles.data.profile.repository.ProfileRepositoryImpl
import ru.mareanexx.feature_profiles.domain.profile.ProfileRepository
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
        profileDao: ru.mareanexx.data.profile.dao.ProfileDao,
        tripDao: ru.mareanexx.data.trip.dao.TripDao,
        tagDao: ru.mareanexx.data.tag.dao.TagDao
    ): ProfileRepository {
        return ProfileRepositoryImpl(userSession, profileApi, profileDao, tripDao, tagDao)
    }
}