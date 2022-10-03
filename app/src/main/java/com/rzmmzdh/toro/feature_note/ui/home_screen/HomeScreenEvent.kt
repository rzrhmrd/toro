package com.rzmmzdh.toro.feature_note.ui.home_screen

import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.NoteCategory


sealed class HomeScreenEvent {
    data class OnNoteDelete(val note: Note) : HomeScreenEvent()
    data class OnSearch(val value: String) : HomeScreenEvent()
    object OnUndoNoteDelete : HomeScreenEvent()
    object OnClearSearchBox : HomeScreenEvent()
    object NotificationDisplayed : HomeScreenEvent()
    data class OnFilterItemSelect(val filter: NoteCategory) : HomeScreenEvent()
    object OnClearFilter : HomeScreenEvent()
}
