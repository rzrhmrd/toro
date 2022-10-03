package com.rzmmzdh.toro.feature_note.ui.home_screen

import com.rzmmzdh.toro.feature_note.domain.model.Note


data class NoteListUiState(
    val loading: Boolean = false,
    val notes: List<Note> = emptyList(),
)
