package com.rzmmzdh.toro.feature_note.ui.viewmodel

sealed class EditNoteEvent {
    object SaveNote : EditNoteEvent()
    object OpenDialog : EditNoteEvent()
    data class OnTitleChanged(val value: String) : EditNoteEvent()
    data class OnBodyChanged(val value: String) : EditNoteEvent()
}