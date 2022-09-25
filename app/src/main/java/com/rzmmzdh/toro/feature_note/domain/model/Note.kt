package com.rzmmzdh.toro.feature_note.domain.model

import com.rzmmzdh.toro.feature_note.data.datasource.local.NoteEntity
import kotlinx.datetime.Instant

data class Note(
    val id: Int = 0,
    var title: String,
    var body: String,
    val lastModificationDate: Instant
)

fun Note.asNoteEntity() =
    NoteEntity(id = id, title = title, body = body, lastModificationDate = lastModificationDate)
