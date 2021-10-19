buildscript {
    val objectboxVersion by extra("3.0.1")

    // For Android projects
    val _compileSdkVersion by extra(31) /* Android 12 (S) */
    val _targetSdkVersion by extra(31) /* Android 12 (S) */

    // For Kotlin projects
    val kotlinVersion by extra("1.5.31")

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3") // For Android projects
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion") // For Kotlin projects
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion")
    }

    repositories {
        mavenCentral() // ObjectBox artifacts are available on Maven Central.
        google() // For Android projects
    }
}

allprojects {
    repositories {
        mavenCentral() // ObjectBox artifacts are available on Maven Central.
        google() // For Android projects
    }
}

// Helper task for us to quickly compress the example files into a ZIP file.
tasks.register<Zip>("zipAll") {
    archiveBaseName.set("objectbox-examples")
    from(rootDir) {
        exclude("**/.idea/**")
        exclude("**/build/**")
        exclude(".gradle/**")
        exclude("**/*.iml")
        exclude("**/*.dll")
        exclude("**/*.so")
        exclude("**/local.properties")
    }

    destinationDirectory.set(buildDir)
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
