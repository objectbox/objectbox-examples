// See the root build.gradle file on how to add plugins and repositories.

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("io.objectbox")
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    compileSdk = _compileSdkVersion
    namespace = "com.example.feature_tasks"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // ObjectBox dependencies are added automatically by the ObjectBox plugin.
}
