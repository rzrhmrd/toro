package com.rzmmzdh.toro.feature_note.domain.model

import com.rzmmzdh.toro.feature_note.data.datasource.local.NoteEntity
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.NoteCategory
import kotlinx.datetime.Instant

data class Note(
    val id: Int = -1,
    val title: String,
    val body: String,
    val category: NoteCategory,
    val lastModificationDate: Instant
)

fun Note.asNoteEntity() =
    NoteEntity(
        id = id,
        title = title,
        body = body,
        category = category,
        lastModificationDate = lastModificationDate
    )
