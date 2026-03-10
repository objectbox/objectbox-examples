plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.objectbox)
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    namespace = "io.objectbox.example"
    compileSdk = _compileSdkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "io.objectbox.example"
        minSdk = 21
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    // For ObjectBox: optionally add Android database library with Admin (for debug builds only)
    // https://docs.objectbox.io/data-browser
    debugImplementation(libs.objectbox.android.admin)
    releaseImplementation(libs.objectbox.android)

    testImplementation(libs.junit)
}
