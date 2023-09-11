// See the root build.gradle file on how to add plugins and repositories.

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("io.objectbox")
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

kotlin {
    // Use JDK 8 to build and use Java 8 compatible code https://developer.android.com/build/jdks
    jvmToolchain(8)
}

android {
    compileSdk = _compileSdkVersion
    namespace = "com.example.feature_notes"

    defaultConfig {
        minSdk = 23
        targetSdk = _targetSdkVersion

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // ObjectBox dependencies are added automatically by the ObjectBox plugin.
}
