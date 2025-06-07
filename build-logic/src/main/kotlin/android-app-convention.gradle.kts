import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import config.appDefaultConfig
import config.baseAndroidConfig
import config.libs

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

configure<BaseAppModuleExtension> {
    baseAndroidConfig()
    appDefaultConfig()

    buildFeatures {
        compose = true
        buildConfig = true
    }

    lint {
        disable += "NullSafeMutableLiveData"
    }

    flavorDimensions += "version"
    productFlavors {
        create("localServer") {
            dimension = "version"
            applicationIdSuffix = ".local"
            versionNameSuffix = "-local"
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8080/api/v1/\"")
            buildConfigField("String", "API_FILES_URL", "\"http://10.0.2.2:8080\"")
        }
        create("remoteServer") {
            dimension = "version"
            applicationIdSuffix = ".remote"
            versionNameSuffix = "-remote"
            buildConfigField("String", "API_BASE_URL", "\"http://130.193.58.87:8080/api/v1/\"")
            buildConfigField("String", "API_FILES_URL", "\"http://130.193.58.87:8080\"")
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}