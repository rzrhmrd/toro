package com.rzmmzdh.toro.feature_note.ui

sealed class HomeScreenEvent {
    object AddNewNote : HomeScreenEvent()
    object DeleteAllNotes : HomeScreenEvent()
}
