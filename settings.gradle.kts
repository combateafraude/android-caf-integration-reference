pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://repo.combateafraude.com/android/release") }
        maven { url = uri("https://raw.githubusercontent.com/iProov/android/master/maven/") }
        maven { setUrl("https://maven.fpregistry.io/releases") }
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "Caf-Integration-Reference"
include(":app")
 