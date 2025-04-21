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
import ru.mareanexx.travelogue.di.utils.TokenAuthenticator
import ru.mareanexx.travelogue.utils.DataStore
import ru.mareanexx.travelogue.utils.LocalDateAdapter
import ru.mareanexx.travelogue.utils.OffsetDateTimeAdapter
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
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
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
        }.build()
    }

    @Provides
    fun provideAuthInterceptor(dataStore: DataStore) : AuthInterceptor {
        return AuthInterceptor(dataStore)
    }

    @Provides
    fun provideTokenAuthenticator(dataStore: DataStore): TokenAuthenticator {
        return TokenAuthenticator(dataStore)
    }
}