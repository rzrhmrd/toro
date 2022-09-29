buildscript {
    dependencies {
        classpath(Plugin.hiltAndroidGradlePlugin)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugin.androidApplication) version Version.androidApplicationPlugin apply false
    id(Plugin.androidLibraryPlugin) version Version.androidApplicationPlugin apply false
    id(Plugin.kotlinAndroid) version Version.kotlinAndroidPlugin apply false
}