package com.rzmmzdh.toro.feature_note.domain.repository

import com.rzmmzdh.toro.feature_note.domain.model.Note

interface NoteRepository {
    suspend fun insertNote(note: Note)
}
