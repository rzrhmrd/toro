package com.rzmmzdh.toro.feature_note.viewmodel

import com.rzmmzdh.toro.feature_note.domain.model.Note


data class NoteListUiState(
    val loading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val error: String = "Error"
)
