// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.android.librairy) apply false
    alias(libs.plugins.build.spotlessGradle)
    id("com.google.devtools.ksp") version libs.versions.kspVersion apply false
}

apply(from = "${rootDir}/spotless.gradle")