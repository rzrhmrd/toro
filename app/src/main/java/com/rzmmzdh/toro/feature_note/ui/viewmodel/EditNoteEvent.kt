package com.rzmmzdh.toro.feature_note.ui.viewmodel

import com.rzmmzdh.toro.feature_note.domain.model.Note

sealed class EditNoteEvent {
    data class SaveNote(val note: Note) : EditNoteEvent()
    data class OnTitleChanged(val value: String) : EditNoteEvent()
    data class OnBodyChanged(val value: String) : EditNoteEvent()
}