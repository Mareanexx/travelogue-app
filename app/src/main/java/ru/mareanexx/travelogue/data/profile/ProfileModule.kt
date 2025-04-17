package ru.mareanexx.travelogue.data.profile

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.profile.local.dao.ProfileDao
import ru.mareanexx.travelogue.data.profile.remote.api.ProfileApi
import ru.mareanexx.travelogue.data.profile.repository.ProfileRepositoryImpl
import ru.mareanexx.travelogue.domain.profile.ProfileRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

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
        profileDao: ProfileDao
    ): ProfileRepository {
        return ProfileRepositoryImpl(userSession, profileApi, profileDao)
    }
}