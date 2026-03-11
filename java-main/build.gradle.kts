// See the root build script on how to add plugins and repositories.

plugins {
    id("application")
    alias(libs.plugins.objectbox)
}

val objectboxVersion: String by rootProject.extra

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClass.set("io.objectbox.example.Main")
}

dependencies {
    implementation("io.objectbox:objectbox-java:$objectboxVersion")

    // Optional: include all native libraries for distribution
    // (only the one for the current platform is added by the plugin).
    implementation("io.objectbox:objectbox-linux:$objectboxVersion")
    implementation("io.objectbox:objectbox-macos:$objectboxVersion")
    implementation("io.objectbox:objectbox-windows:$objectboxVersion")

    testImplementation("junit:junit:4.13.2")
}
