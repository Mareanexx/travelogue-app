package ru.mareanexx.travelogue.di

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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(BuildConfig.API_BASE_URL)
        }.build()
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