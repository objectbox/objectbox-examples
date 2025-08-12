// See the root build script on how to add plugins and repositories.

plugins {
    id("java")
    id("application")
}

targetCompatibility = '1.8'
sourceCompatibility = '1.8'

mainClassName = "io.objectbox.example.Main"

dependencies {
    implementation "io.objectbox:objectbox-java:$objectboxVersion"
    implementation "org.greenrobot:essentials:3.1.0"
    implementation "com.beust:jcommander:1.82"

    // To run on ARM-based Linux need to manually include libraries.
    implementation "io.objectbox:objectbox-linux-arm64:$objectboxVersion"
    implementation "io.objectbox:objectbox-linux-armv7:$objectboxVersion"
}

// Apply plugin after dependencies block so they are not overwritten.
apply plugin: 'io.objectbox'
