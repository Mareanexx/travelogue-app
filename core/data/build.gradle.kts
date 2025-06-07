plugins {
    id("android-core-data-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    implementation(project(":core:common"))
    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.compiler)
}