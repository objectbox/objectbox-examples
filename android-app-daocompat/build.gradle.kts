plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.objectbox)
}

val _compileSdkVersion: Int by rootProject.extra
val _targetSdkVersion: Int by rootProject.extra

android {
    namespace = "io.objectbox.example.daocompat"
    compileSdk = _compileSdkVersion

    buildFeatures {
        buildConfig = true
    }

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
        // While ObjectBox only requires Java 8, this is deprecated and
        // new Android projects should use 11.
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.objectbox.daocompat)
}
