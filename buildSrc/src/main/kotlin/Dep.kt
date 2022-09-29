object Dep {

    //Test
    const val junit = "junit:junit:${Version.junit}"
    const val junitAndroid = "androidx.test.ext:junit:${Version.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espressoCore}"
    const val UiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Version.compose}"

    //Compose
    const val composeUi = "androidx.compose.ui:ui:${Version.compose}"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Version.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Version.compose}"
    const val activityCompose =
        "androidx.activity:activity-compose:${Version.activityCompose}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Version.compose}"
    const val material3 = "androidx.compose.material3:material3:${Version.material3}"
    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Version.composeNavigation}"
    const val accompanistSystemUiController =
        "com.google.accompanist:accompanist-systemuicontroller:${Version.accompanist}"
    const val accompanistNavigationAnimation =
        "com.google.accompanist:accompanist-navigation-animation:${Version.accompanist}"

    //Room
    const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.room}"

    //Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.hilt}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Version.hiltNavigationCompose}"

    //DataStore
    const val dataStore = "androidx.datastore:datastore:${Version.dataStore}"
    const val protobufJavaLite =
        "com.google.protobuf:protobuf-javalite:${Version.protobufJavaLite}"

    //Misc
    const val kotlinxDateTime =
        "org.jetbrains.kotlinx:kotlinx-datetime:${Version.kotlinxDateTime}"
}
