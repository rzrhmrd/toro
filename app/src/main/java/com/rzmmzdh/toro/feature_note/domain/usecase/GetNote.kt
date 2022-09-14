package com.rzmmzdh.toro.feature_note.domain.usecase

import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNote @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(id: Int): Flow<Note> {
        return repository.getNote(id)
    }
}