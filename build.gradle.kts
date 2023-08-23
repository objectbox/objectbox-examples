plugins {
    // Plugin to help us find updated dependencies, not required to use ObjectBox
    // https://github.com/ben-manes/gradle-versions-plugin/releases
    id("com.github.ben-manes.versions") version "0.47.0"
}

buildscript {
    val objectboxVersion by extra("3.6.0")

    // For Android projects
    val _compileSdkVersion by extra(33) /* Android 13 (T) */
    val _targetSdkVersion by extra(32) /* Android 12 (S V2) */

    // For Kotlin projects
    val kotlinVersion by extra("1.7.22")

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1") // For Android projects
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

// Reject preview releases for dependencyUpdates task
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
