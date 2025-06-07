pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
        }
    }
}

rootProject.name = "Travelogue"
include(
    ":app",
    ":core",
    ":core:network",
    ":core:common",
    ":core:data",
    ":feature-auth",
    ":feature-profiles",
    ":feature-notifications",
    ":feature-explore"
)