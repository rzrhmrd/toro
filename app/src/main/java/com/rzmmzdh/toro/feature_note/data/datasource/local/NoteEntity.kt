package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rzmmzdh.toro.feature_note.domain.model.Note
import kotlinx.datetime.Instant

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val lastModificationDate: Instant
)

fun NoteEntity.toNote() =
    Note(id = id, title = title, body = body, lastModificationDate = lastModificationDate)
