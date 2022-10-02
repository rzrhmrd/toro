package com.rzmmzdh.toro.feature_note.domain.usecase

data class NoteUseCases(
    val getNote: GetNote,
    val getAllNotes: GetAllNotes,
    val insertNote: InsertNote,
    val deleteNote: DeleteNote,
    val deleteAllNotes: DeleteAllNotes,
    val searchNotes: SearchNotes,
    val getNotesByCategory: GetNotesByCategory
)
