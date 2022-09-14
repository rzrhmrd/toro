package com.rzmmzdh.toro.feature_note.data.datasource.local

import com.rzmmzdh.toro.feature_note.domain.model.Note


interface NoteDataSource {
    suspend fun insetNote(note: Note)
}
