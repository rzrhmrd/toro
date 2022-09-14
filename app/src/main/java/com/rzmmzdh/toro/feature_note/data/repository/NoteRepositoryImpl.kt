package com.rzmmzdh.toro.feature_note.data.repository

import com.rzmmzdh.toro.feature_note.data.datasource.NoteDataSource
import com.rzmmzdh.toro.feature_note.domain.model.Note
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val localDataSource: NoteDataSource) :
    NoteRepository {
    override suspend fun insertNote(note: Note) {
        localDataSource.insertNote(note)
    }
}