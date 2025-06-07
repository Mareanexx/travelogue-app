package ru.mareanexx.network.data.likes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.network.data.likes.remote.api.LikesApi
import ru.mareanexx.network.data.likes.repository.LikesRepositoryImpl
import ru.mareanexx.network.domain.likes.LikesRepository
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