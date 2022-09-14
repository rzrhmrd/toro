package com.rzmmzdh.toro.feature_note.data.datasource.local

import com.rzmmzdh.toro.feature_note.data.datasource.NoteDataSource
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.model.toNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val noteDao: NoteDao) : NoteDataSource {
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toNoteEntity())
    }

    override fun getNote(id: Int): Flow<Note> {
        return noteDao.getNote(id).map { it.toNote() }
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().mapNotNull { it.map { it.toNote() } }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toNoteEntity())
    }
}