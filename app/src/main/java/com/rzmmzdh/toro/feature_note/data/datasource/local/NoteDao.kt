package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM noteentity WHERE id = :id")
    fun getNote(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM noteentity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Delete
    fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM noteentity")
    suspend fun deleteAllNotes()
}
