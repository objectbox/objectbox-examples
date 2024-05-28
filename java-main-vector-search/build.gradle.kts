// See the root build script on how to add plugins and repositories.

plugins {
    id("application")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    // Note: Gradle defaults to the platform default encoding, make sure to always use UTF-8.
    options.encoding = "UTF-8"
}

tasks.named<JavaExec>("run") {
    // Let Gradle run accept console input
    standardInput = System.`in`
}

application {
    mainClass.set("io.objectbox.example.java.vectorsearch.Main")
}

val objectboxVersion: String by rootProject.extra

dependencies {
    implementation("io.objectbox:objectbox-java:$objectboxVersion")

    // Optional: include all native libraries for distribution
    // (only the one for the current platform is added by the plugin).
    implementation("io.objectbox:objectbox-linux:$objectboxVersion")
    implementation("io.objectbox:objectbox-macos:$objectboxVersion")
    implementation("io.objectbox:objectbox-windows:$objectboxVersion")
}

// Apply the plugin after the dependencies block so it detects added
// ObjectBox dependencies and does not replace them.
apply(plugin = "io.objectbox")