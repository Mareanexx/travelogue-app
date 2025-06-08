import config.BuildConfigConsts
import config.android
import config.baseAndroidConfig
import config.libs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.mareanexx.core.data"
    baseAndroidConfig()

    defaultConfig {
        minSdk = BuildConfigConsts.MIN_SDK
        testOptions.targetSdk = BuildConfigConsts.TARGET_SDK

        testInstrumentationRunner = BuildConfigConsts.TEST_INSTRUMENTATION_RUNNER
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}