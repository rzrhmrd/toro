package com.rzmmzdh.toro.feature_note.domain.usecase

import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNotes @Inject constructor(val noteRepository: NoteRepository) {
    operator fun invoke(query: String): Flow<List<Note>> {
        return noteRepository.searchNotes(query)
    }

}
