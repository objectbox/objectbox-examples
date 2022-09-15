// See the root build.gradle file on how to add plugins and repositories.

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("application")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
