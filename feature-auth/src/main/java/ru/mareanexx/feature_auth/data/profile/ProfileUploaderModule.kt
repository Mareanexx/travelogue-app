package ru.mareanexx.feature_auth.data.profile

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.data.profile.dao.ProfileDao
import ru.mareanexx.feature_auth.data.profile.remote.api.UploadProfileApi
import ru.mareanexx.feature_auth.data.profile.repository.UploadProfileRepositoryImpl
import ru.mareanexx.feature_auth.domain.profile.UploadProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileUploaderModule {
    @Singleton
    @Provides
    fun provideProfileUploaderRepository(
        userSessionManager: UserSessionManager,
        uploadProfileApi: UploadProfileApi,
        profileDao: ProfileDao
    ): UploadProfileRepository {
        return UploadProfileRepositoryImpl(userSessionManager, uploadProfileApi, profileDao)
    }

    @Provides
    @Singleton
    fun provideProfileUploaderApi(retrofit: Retrofit): UploadProfileApi {
        return retrofit.create(UploadProfileApi::class.java)
    }
}