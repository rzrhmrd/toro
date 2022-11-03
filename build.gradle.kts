buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.android.tools.build:gradle:7.4.0-beta04")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
    }
} // Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0-beta04" apply false
    id("com.android.library") version "7.4.0-beta04" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}
