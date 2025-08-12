// See the root build script on how to add plugins and repositories.

plugins {
    id("com.android.application")
    id("io.objectbox")
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    namespace = "io.objectbox.example.daocompat"
    compileSdk = _compileSdkVersion

    defaultConfig {
        applicationId = "io.objectbox.example.daocompat"
        minSdk = 21
        targetSdk = _targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("objectbox.daoCompat" to "true")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("org.greenrobot:objectbox-daocompat:4.2.0")
}
