package com.rzmmzdh.toro.feature_note.ui.edit_note_screen

sealed class EditNoteEvent {
    object OnNoteSave : EditNoteEvent()
    object OnAlertDismiss : EditNoteEvent()
    data class OnCategorySelect(val category: NoteCategory) : EditNoteEvent()
    data class OnTitleChange(val value: String) : EditNoteEvent()
    data class OnBodyChange(val value: String) : EditNoteEvent()
}