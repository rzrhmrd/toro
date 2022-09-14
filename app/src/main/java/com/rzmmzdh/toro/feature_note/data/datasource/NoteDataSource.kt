package com.rzmmzdh.toro.feature_note.data.datasource

import com.rzmmzdh.toro.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


interface NoteDataSource {
    suspend fun insertNote(note: Note)
    fun getNote(id: Int): Flow<Note>
    fun getAllNotes(): Flow<List<Note>>
}
