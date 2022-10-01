package com.rzmmzdh.toro.feature_note.ui.edit_note_screen

sealed class EditNoteEvent {
    object SaveNote : EditNoteEvent()
    object ShowAlert : EditNoteEvent()
    object AlertShown : EditNoteEvent()
    data class CategorySelected(val category: NoteCategory) : EditNoteEvent()
    data class OnTitleChanged(val value: String) : EditNoteEvent()
    data class OnBodyChanged(val value: String) : EditNoteEvent()
}