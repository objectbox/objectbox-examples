// See the root build.gradle file on how to add plugins and repositories.

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra
val objectboxVersion: String by rootProject.extra
val kotlinVersion: String by rootProject.extra

android {
    namespace = "io.objectbox.example.sync"
    compileSdk = _compileSdkVersion

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = "io.objectbox.example.sync.kotlin"
        minSdk = 21
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")

    // ObjectBox with Data Browser for debug builds, without for release builds.
    // https://docs.objectbox.io/data-browser
    debugImplementation("io.objectbox:objectbox-sync-android-objectbrowser:$objectboxVersion")
    releaseImplementation("io.objectbox:objectbox-sync-android:$objectboxVersion")
}

// apply the plugin after the dependencies block so it does not automatically add objectbox-android
// which would conflict with objectbox-android-objectbrowser on debug builds
apply(plugin = "io.objectbox.sync")