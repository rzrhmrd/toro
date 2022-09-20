package com.rzmmzdh.toro.feature_note.ui.core

sealed class Screens(val route: String) {
    object Home : Screens("/home_screen")
    object EditNote : Screens("/edit_note")
    object Settings : Screens("/settings")
}
