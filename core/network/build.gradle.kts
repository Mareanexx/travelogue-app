plugins {
    id("android-core-network-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    ksp(libs.hilt.compiler)
}