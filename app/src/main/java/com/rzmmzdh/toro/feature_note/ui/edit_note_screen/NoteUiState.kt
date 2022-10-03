package com.rzmmzdh.toro.feature_note.ui.edit_note_screen

import com.rzmmzdh.toro.feature_note.domain.model.Note
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class NoteUiState(
    val id: Int = -1,
    val title: String = "",
    val body: String = "",
    val category: NoteCategory = NoteCategory.FREE,
    val lastModificationDate: Instant = Clock.System.now(),
    val isEmpty: Boolean = false
)
