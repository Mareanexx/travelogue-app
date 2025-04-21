package ru.mareanexx.travelogue.data.notifications

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.notifications.remote.api.NotificationsApi
import ru.mareanexx.travelogue.data.notifications.repository.NotificationsRepositoryImpl
import ru.mareanexx.travelogue.domain.notifications.NotificationsRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
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