package com.rzmmzdh.toro.feature_note.domain.usecase

import com.rzmmzdh.toro.feature_note.domain.repository.NoteRepository
import com.rzmmzdh.toro.feature_note.domain.model.Note
import javax.inject.Inject

class InsertNote @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        noteRepository.insertNote(note)
    }
}