// See the root build script on how to add plugins and repositories.

plugins {
    id("com.android.application")
    id("io.objectbox")
}

android {
    namespace "io.objectbox.example.relation"
    compileSdkVersion _compileSdkVersion

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId "io.objectbox.example.relation"
        minSdkVersion 21
        targetSdkVersion _targetSdkVersion
        versionCode 1
        versionName "1.0"
    }
}
