package ru.mareanexx.travelogue.data.likes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.travelogue.data.likes.remote.api.LikesApi
import ru.mareanexx.travelogue.data.likes.repository.LikesRepositoryImpl
import ru.mareanexx.travelogue.domain.likes.LikesRepository
import ru.mareanexx.travelogue.utils.UserSessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LikesModule {
    @Singleton
    @Provides
    fun provideLikesApi(retrofit: Retrofit) : LikesApi {
        return retrofit.create(LikesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLikesRepository(
        userSession: UserSessionManager,
        likesApi: LikesApi
    ): LikesRepository {
        return LikesRepositoryImpl(userSession, likesApi)
    }
}