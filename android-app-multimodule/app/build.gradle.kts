// See the root build.gradle file on how to add plugins and repositories.

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra
val objectboxVersion: String by rootProject.extra

android {
    compileSdk = _compileSdkVersion

    defaultConfig {
        applicationId = "com.example.android_app_multimodule"
        minSdk = 23
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    testOptions {
        unitTests {
            // For Robolectric tests
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":android-app-multimodule:feature_notes"))
    implementation(project(":android-app-multimodule:feature_tasks"))

    implementation("androidx.activity:activity-ktx:1.5.1")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("org.robolectric:robolectric:4.8.1")
    // Add the ObjectBox native library to run unit tests on this development machine.
    // A library for Linux (x86_64), Windows (x86 and x86_64) and macOS (x86 and M1) is available.
    testImplementation("io.objectbox:objectbox-linux:$objectboxVersion")
    testImplementation("io.objectbox:objectbox-macos:$objectboxVersion")
    testImplementation("io.objectbox:objectbox-windows:$objectboxVersion")
    // There are also ARM versions available for Linux,
    // but these are typically not architectures used on development machines.
    // testImplementation("io.objectbox:objectbox-linux-arm64:$objectboxVersion")
    // testImplementation("io.objectbox:objectbox-linux-armv7:$objectboxVersion")
}
