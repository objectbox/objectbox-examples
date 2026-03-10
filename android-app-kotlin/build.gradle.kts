import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // For ObjectBox: apply the kapt and ObjectBox plugin
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.objectbox)
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    namespace = "io.objectbox.example.kotlin"
    compileSdk = _compileSdkVersion

    defaultConfig {
        applicationId = "io.objectbox.example.kotlin"
        minSdk = 21
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            // R8/ProGuard rules for ObjectBox are included in the Java and Android dependencies,
            // no additional configuration is required.
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.material)

    // For ObjectBox: optionally add Android database library with Admin (for debug builds only)
    // https://docs.objectbox.io/data-browser
    debugImplementation(libs.objectbox.android.admin)
    releaseImplementation(libs.objectbox.android)

    testImplementation(libs.junit)
}
