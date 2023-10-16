// See the root build.gradle file on how to add plugins and repositories.

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("application")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType(type = org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

// Since Kotlin 1.8 kapt not longer inherits the JVM target version from the Kotlin compile tasks
// https://youtrack.jetbrains.com/issue/KT-55947/Unable-to-set-kapt-jvm-target-version
tasks.withType(type = org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask::class) {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

application {
    mainClass.set("io.objectbox.example.Main")
}

val objectboxVersion: String by rootProject.extra

dependencies {
    implementation("io.objectbox:objectbox-java:$objectboxVersion")

    // Optional: include all native libraries for distribution
    // (only the one for the current platform is added by the plugin).
    implementation("io.objectbox:objectbox-linux:$objectboxVersion")
    implementation("io.objectbox:objectbox-macos:$objectboxVersion")
    implementation("io.objectbox:objectbox-windows:$objectboxVersion")

    testImplementation("junit:junit:4.13.2")
}

// Apply the plugin after the dependencies block so it detects added
// ObjectBox dependencies and does not replace them.
apply(plugin = "io.objectbox")
