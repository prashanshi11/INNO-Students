// Top-level build.gradle.kts for the project (InnoStudent)

plugins {
    // No plugins needed here, managed in settings.gradle.kts or app/build.gradle.kts
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Firebase Crashlytics Gradle plugin (optional, included for completeness)
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.android.tools.build:gradle:8.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
    }
}
