package com.rzmmzdh.toro.feature_note.ui.core

sealed class Screen(val route: String) {
    object Home : Screen("/home_screen/")

    object EditNote : Screen("/edit_note/") {
        fun withNoteId(noteId: Int): String {
            return this.route + "?editNoteId=" + noteId
        }

    }
}
