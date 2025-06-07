package ru.mareanexx.feature_notifications.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_notifications.data.remote.api.NotificationsApi
import ru.mareanexx.feature_notifications.data.repository.NotificationsRepositoryImpl
import ru.mareanexx.feature_notifications.domain.NotificationsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsModule {
    @Singleton
    @Provides
    fun provideNotificationsApi(retrofit: Retrofit): NotificationsApi {
        return retrofit.create(NotificationsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNotificationsRepository(
        notificationsApi: NotificationsApi,
        userSessionManager: UserSessionManager
    ): NotificationsRepository {
        return NotificationsRepositoryImpl(notificationsApi, userSessionManager)
    }
}