buildscript {
    dependencies {
        classpath(Dep.hiltAndroidGradlePlugin)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Dep.androidApplicationPlugin) version Version.androidApplicationPlugin apply false
    id(Dep.androidLibraryPlugin) version Version.androidApplicationPlugin apply false
    id(Dep.kotlinAndroidPlugin) version Version.kotlinAndroidPlugin apply false
}