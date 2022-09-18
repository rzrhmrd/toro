package com.rzmmzdh.toro.feature_note.domain.usecase

import com.rzmmzdh.toro.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteAllNotes @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke() {
        repository.deleteAllNotes()
    }
}