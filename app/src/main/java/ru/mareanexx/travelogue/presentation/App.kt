package ru.mareanexx.travelogue.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.travelogue.BuildConfig

@HiltAndroidApp
class TravelogueApp : Application() {
    override fun onCreate() {
        super.onCreate()

        ApiConfig.apiBaseUrl = BuildConfig.API_BASE_URL
        ApiConfig.apiFilesUrl = BuildConfig.API_FILES_URL
    }
}