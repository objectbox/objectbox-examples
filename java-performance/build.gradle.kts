plugins {
    id("application")
    alias(libs.plugins.objectbox)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClass.set("io.objectbox.example.Main")
}

dependencies {
    implementation(libs.essentials)
    implementation(libs.jcommander)

    // To run on ARM-based Linux need to manually include additional database libraries
    implementation(libs.objectbox.linux.arm64)
    implementation(libs.objectbox.linux.armv7)
}
