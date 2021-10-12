// See the root build.gradle file on how to add plugins and repositories.

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

val objectboxVersion: String by rootProject.extra
val kotlinVersion: String by rootProject.extra
val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    compileSdk = _compileSdkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    defaultConfig {
        applicationId = "io.objectbox.example.kotlin"
        minSdk = 16
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.activity:activity-ktx:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    val lifecycleVersion = "2.3.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("com.google.android.material:material:1.3.0")

    // ObjectBox with Data Browser for debug builds, without for release builds.
    // https://docs.objectbox.io/data-browser
    debugImplementation("io.objectbox:objectbox-android-objectbrowser:$objectboxVersion")
    releaseImplementation("io.objectbox:objectbox-android:$objectboxVersion")
}

// apply the plugin after the dependencies block so it does not automatically add objectbox-android
// which would conflict with objectbox-android-objectbrowser on debug builds
apply(plugin = "io.objectbox")
