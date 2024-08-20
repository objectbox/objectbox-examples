// See the root build script on how to add plugins and repositories.

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra
val objectboxVersion: String by rootProject.extra

android {
    namespace = "io.objectbox.example.sync"
    compileSdk = _compileSdkVersion

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "io.objectbox.example.sync.kotlin"
        minSdk = 21
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

// Since Kotlin 1.8 kapt not longer inherits the JVM target version from the Kotlin compile tasks
// https://youtrack.jetbrains.com/issue/KT-55947/Unable-to-set-kapt-jvm-target-version
tasks.withType(type = org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask::class) {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

dependencies {
    implementation("androidx.activity:activity-ktx:1.9.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    // ObjectBox with Data Browser for debug builds, without for release builds.
    // https://docs.objectbox.io/data-browser
    debugImplementation("io.objectbox:objectbox-sync-android-objectbrowser:$objectboxVersion")
    releaseImplementation("io.objectbox:objectbox-sync-android:$objectboxVersion")
}

// apply the plugin after the dependencies block so it does not automatically add objectbox-android
// which would conflict with objectbox-android-objectbrowser on debug builds
apply(plugin = "io.objectbox.sync")
