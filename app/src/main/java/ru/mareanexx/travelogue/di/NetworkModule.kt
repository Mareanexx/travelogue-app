package ru.mareanexx.travelogue.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.di.utils.AuthInterceptor
import ru.mareanexx.travelogue.utils.DataStore
import ru.mareanexx.travelogue.utils.LocalDateAdapter
import ru.mareanexx.travelogue.utils.OffsetDateTimeAdapter
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
            baseUrl(BuildConfig.API_BASE_URL)
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
    fun provideAuthInterceptor(dataStore: DataStore) : AuthInterceptor {
        return AuthInterceptor(dataStore)
    }
}