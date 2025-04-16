package ru.mareanexx.travelogue.di.utils

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.mareanexx.travelogue.utils.DataStore

class TokenAuthenticator(
    private val dataStore: DataStore
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }

}