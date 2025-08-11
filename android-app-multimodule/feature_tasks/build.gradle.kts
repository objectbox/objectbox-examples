import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// See the root build script on how to add plugins and repositories.

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
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_1_8
    }
}

dependencies {
    // ObjectBox dependencies are added automatically by the ObjectBox plugin.
}
