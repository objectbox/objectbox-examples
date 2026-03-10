plugins {
    id("application")
    alias(libs.plugins.objectbox)
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

dependencies {
    // For ObjectBox: optionally add database libraries for all platforms supported by your
    // application. If you add none, the ObjectBox Gradle plugin automatically adds the one matching
    // your current machine.
    implementation(libs.objectbox.linux)
    implementation(libs.objectbox.linux.arm64)
    implementation(libs.objectbox.linux.armv7)
    implementation(libs.objectbox.macos)
    implementation(libs.objectbox.windows)
}
