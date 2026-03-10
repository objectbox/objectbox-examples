pluginManagement {
    repositories {
        // For Android projects
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        // The ObjectBox plugin is available on Maven Central
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // For Android projects
        google()
        // ObjectBox dependencies are available on Maven Central
        mavenCentral()
    }
}

include("android-app")
include("android-app-arch")
include("android-app-daocompat")
include("android-app-kotlin")
include("android-app-multiprocess")
include("android-app-relations")
include("java-main")
include("java-performance")
include("kotlin-main")
include(":android-app-multimodule:app")
include(":android-app-multimodule:feature_notes")
include(":android-app-multimodule:feature_tasks")
include(":java-main-vector-search")
