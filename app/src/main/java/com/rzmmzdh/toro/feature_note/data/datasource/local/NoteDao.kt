package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.Dao
import com.rzmmzdh.toro.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    suspend fun insertNote(note: Note)
    fun getNote(id: Int): Flow<Note>
}
