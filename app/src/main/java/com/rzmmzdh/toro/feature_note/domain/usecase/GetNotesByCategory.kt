package com.rzmmzdh.toro.feature_note.domain.usecase

import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.repository.NoteRepository
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.NoteCategory
import kotlinx.coroutines.flow.Flow


class GetNotesByCategory constructor(private val repository: NoteRepository) {
    operator fun invoke(category: NoteCategory): Flow<List<Note>> {
        return repository.getNotesByCategory(category)
    }
}