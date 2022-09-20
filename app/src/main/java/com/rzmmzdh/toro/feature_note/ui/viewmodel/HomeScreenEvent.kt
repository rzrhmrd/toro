package com.rzmmzdh.toro.feature_note.ui.viewmodel

import com.rzmmzdh.toro.feature_note.domain.model.Note


sealed class HomeScreenEvent {
    object AddNewNote : HomeScreenEvent()
    object DeleteAllNotes : HomeScreenEvent()
    object ToggleTheme : HomeScreenEvent()
    data class DeleteNote(val note: Note) : HomeScreenEvent()
}
