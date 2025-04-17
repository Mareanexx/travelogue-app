package ru.mareanexx.travelogue.di.utils

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.mareanexx.travelogue.utils.DataStore
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val dataStore: DataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStore.getToken()
        }

        val newRequest = chain.request().newBuilder().apply {
            if (token.isNotEmpty()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()

        return chain.proceed(newRequest)
    }
}