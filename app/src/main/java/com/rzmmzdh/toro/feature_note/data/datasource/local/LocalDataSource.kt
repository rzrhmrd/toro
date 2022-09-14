package com.rzmmzdh.toro.feature_note.data.datasource.local

import com.rzmmzdh.toro.feature_note.data.datasource.NoteDataSource
import com.rzmmzdh.toro.feature_note.domain.model.Note
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val noteDao: NoteDao) : NoteDataSource {
    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }
}