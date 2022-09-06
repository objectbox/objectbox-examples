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
    namespace = "io.objectbox.example.kotlin"
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
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.activity:activity-ktx:1.5.1")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("com.google.android.material:material:1.6.1")

    // ObjectBox with Data Browser for debug builds, without for release builds.
    // https://docs.objectbox.io/data-browser
    debugImplementation("io.objectbox:objectbox-android-objectbrowser:$objectboxVersion")
    releaseImplementation("io.objectbox:objectbox-android:$objectboxVersion")

    testImplementation("junit:junit:4.13.2")
}

// apply the plugin after the dependencies block so it does not automatically add objectbox-android
// which would conflict with objectbox-android-objectbrowser on debug builds
apply(plugin = "io.objectbox")
