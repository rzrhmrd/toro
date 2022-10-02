package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.*
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.NoteCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM noteentity WHERE id = :id")
    fun getNote(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM noteentity ORDER BY lastModificationDate DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM noteentity")
    suspend fun deleteAllNotes()

    @Query(
        "SELECT * FROM NoteEntity WHERE title LIKE '%' || :query || '%' OR body LIKE '%' || :query || '%'" +
                " ORDER BY lastModificationDate DESC"
    )
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("SELECT * FROM NoteEntity WHERE category = :category")
    fun getNotesByCategory(category: NoteCategory): Flow<List<Note>>

}
