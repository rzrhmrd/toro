package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.*
import com.rzmmzdh.toro.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM noteentity WHERE id = :id")
    fun getNote(id: Int): Flow<Note>

    @Query("SELECT * FROM noteentity")
    fun getAllNotes(): Flow<List<Note>>

    @Delete
    fun deleteNote(note: Note)
}
