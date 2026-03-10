import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.objectbox)
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    namespace = "io.objectbox.example.arch"
    compileSdk = _compileSdkVersion

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "io.objectbox.example.arch"
        minSdk = 21
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"
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
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
}
