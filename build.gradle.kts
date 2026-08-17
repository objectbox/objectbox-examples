plugins {
    // Plugin to help us find updated dependencies, not required to use ObjectBox
    alias(libs.plugins.versions)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    // For ObjectBox: add the kapt and ObjectBox plugin
    alias(libs.plugins.android.kapt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.objectbox) apply false
}

buildscript {
    // For Android projects
    val _compileSdkVersion by extra(35) /* Android 15 */
    val _targetSdkVersion by extra(33) /* Android 13 (TIRAMISU) */
}

tasks.register<Zip>("zipAll") {
    description = "Helper task to quickly compress the example files into a ZIP file"
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

    destinationDirectory.set(layout.buildDirectory)
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

// Reject preview releases for dependencyUpdates task
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
