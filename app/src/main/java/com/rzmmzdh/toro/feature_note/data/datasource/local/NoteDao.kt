package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.Dao
import com.rzmmzdh.toro.feature_note.domain.model.Note

@Dao
interface NoteDao {
    suspend fun insetNote(note: Note)
}
