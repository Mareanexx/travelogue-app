import config.BuildConfigConsts
import config.android
import config.baseAndroidConfig
import config.libs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.mareanexx.core.common"
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
    implementation(libs.androidx.material3)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Data Store
    implementation(libs.datastore.preferences)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)

    debugImplementation(libs.androidx.ui.tooling)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}