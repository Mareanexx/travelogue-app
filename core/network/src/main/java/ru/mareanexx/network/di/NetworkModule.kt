package ru.mareanexx.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.network.utils.adapters.LocalDateAdapter
import ru.mareanexx.network.utils.adapters.OffsetDateTimeAdapter
import ru.mareanexx.network.utils.interceptors.AuthInterceptor
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttp)
            baseUrl(ApiConfig.apiBaseUrl)
        }.build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeAdapter())
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttp(requestInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(requestInterceptor)
        }.build()
    }

    @Provides
    fun provideAuthInterceptor(userSessionManager: UserSessionManager) : AuthInterceptor {
        return AuthInterceptor(userSessionManager)
    }
}