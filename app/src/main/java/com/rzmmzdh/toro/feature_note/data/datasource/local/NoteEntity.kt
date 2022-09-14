package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rzmmzdh.toro.feature_note.domain.model.Note
import java.util.*

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val lastModificationDate: Date
)

fun NoteEntity.toNote() =
    Note(id = id, title = title, body = body, lastModificationDate = lastModificationDate)
