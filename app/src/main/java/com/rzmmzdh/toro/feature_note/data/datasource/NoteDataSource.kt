package com.rzmmzdh.toro.feature_note.data.datasource

import com.rzmmzdh.toro.feature_note.domain.model.Note


interface NoteDataSource {
    suspend fun insertNote(note: Note)
}
