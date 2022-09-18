package com.rzmmzdh.toro.feature_note.ui

sealed class Destination(val route: String) {
    object HomeScreen : Destination("/home_screen")
}
