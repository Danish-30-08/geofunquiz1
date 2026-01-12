// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Defines the Android Application plugin and makes it available to sub-modules
    // The alias 'android-application' will be defined in libs.versions.toml
    alias(libs.plugins.android.application) apply false

    // Defines the Kotlin Android plugin
    // The alias 'kotlin-android' will be defined in libs.versions.toml
    alias(libs.plugins.kotlin.android) apply false

    // Defines the Google Services plugin
    // The alias 'google-gms-google-services' will be defined in libs.versions.toml
    alias(libs.plugins.google.gms.google.services) apply false
}
