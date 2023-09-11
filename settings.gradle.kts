 plugins {
    // Projects use the Gradle toolchain feature to use specific JDKs for compilation.
    // This plugin helps auto-download them if not installed locally.
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.7.0")
}

include("android-app")
include("android-app-arch")
include("android-app-daocompat")
include("android-app-kotlin")
include("android-app-multiprocess")
include("android-app-relations")
include("android-app-sync")
include("android-app-sync-kotlin")
include("java-main")
include("java-performance")
include("kotlin-main")
include(":android-app-multimodule:app")
include(":android-app-multimodule:feature_notes")
include(":android-app-multimodule:feature_tasks")
