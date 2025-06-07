plugins {
    id("android-feature-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ru.mareanexx.feature_profiles"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    ksp(libs.hilt.compiler)

    // Mapbox
    implementation(libs.mapbox.android)
    implementation(libs.mapbox.compose)

    // location
    implementation(libs.play.services.location)
}