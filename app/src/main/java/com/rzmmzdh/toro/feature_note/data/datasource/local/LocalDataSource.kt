package com.rzmmzdh.toro.feature_note.data.datasource.local

import com.rzmmzdh.toro.feature_note.data.datasource.NoteDataSource
import com.rzmmzdh.toro.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val noteDao: NoteDao) : NoteDataSource {
    override suspend fun insertNote(note: Note) {
        noteDao.insetNote(note)
    }

    override fun getNote(id: Int): Flow<Note> {
        return noteDao.getNote(id)
    }
}