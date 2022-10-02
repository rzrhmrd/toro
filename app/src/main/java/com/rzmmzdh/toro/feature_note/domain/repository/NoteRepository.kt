package com.rzmmzdh.toro.feature_note.domain.repository

import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.NoteCategory
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note)
    fun getNote(id: Int): Flow<Note>
    fun getAllNotes(): Flow<List<Note>>
    suspend fun deleteNote(note: Note)
    suspend fun deleteAllNotes()
    fun searchNotes(query: String): Flow<List<Note>>
    fun getNotesByCategory(category: NoteCategory): Flow<List<Note>>
}
