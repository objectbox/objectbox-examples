import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    compileSdk = _compileSdkVersion
    namespace = "com.example.android_app_multimodule"

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

    testOptions {
        unitTests {
            // For Robolectric tests
            isIncludeAndroidResources = true
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
    implementation(project(":android-app-multimodule:feature_notes"))
    implementation(project(":android-app-multimodule:feature_tasks"))

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.robolectric)

    // As the ObjectBox plugin is not applied, which would automatically add a database library for
    // local unit tests that matches the current platform, need to add one manually.
    // As we run unit tests on various platforms, we are adding all of them.
    // Libraries for Linux (x86_64, 64-bit ARM), Windows (x86 and x86_64) and macOS (x86 and M1) are
    // available.
    testImplementation(libs.objectbox.linux)
    testImplementation(libs.objectbox.linux.arm64)
    testImplementation(libs.objectbox.macos)
    testImplementation(libs.objectbox.windows)
    // There is also a 32-bit ARM version available for Linux,
    // but this is typically not a platform used to run unit tests on.
    // testImplementation(libs.objectbox.linux.armv7)
}
