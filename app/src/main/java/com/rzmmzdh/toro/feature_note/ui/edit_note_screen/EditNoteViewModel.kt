package com.rzmmzdh.toro.feature_note.ui.edit_note_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var currentNote = mutableStateOf(NoteUiState())
        private set

    init {
        getNoteFromHomeScreen(savedStateHandle)
    }

    private fun getNoteFromHomeScreen(savedStateHandle: SavedStateHandle) {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                currentNote.value = currentNote.value.copy(id = noteId)
                if (currentNote.value.id != -1) {
                    noteUseCases.getNote(noteId).collectLatest { note ->
                        currentNote.value =
                            currentNote.value.copy(
                                id = note.id,
                                title = note.title,
                                body = note.body,
                                category = note.category,
                                lastModificationDate = note.lastModificationDate
                            )
                    }
                }
            }
        }
    }

    fun onEvent(event: EditNoteEvent) {
        viewModelScope.launch {
            when (event) {
                is EditNoteEvent.OnNoteSave -> saveNote()
                is EditNoteEvent.OnTitleChange -> currentNote.value =
                    currentNote.value.copy(title = event.value)
                is EditNoteEvent.OnBodyChange -> currentNote.value =
                    currentNote.value.copy(body = event.value)
                is EditNoteEvent.OnCategorySelect -> currentNote.value =
                    currentNote.value.copy(category = event.category)
                is EditNoteEvent.OnAlertDismiss -> currentNote.value =
                    currentNote.value.copy(isEmpty = false)
            }
        }

    }

    private suspend fun saveNote() {
        if (currentNote.value.title.isNotBlank() || currentNote.value.body.isNotBlank()) {
            if (currentNote.value.id != -1) {
                currentNote.value = currentNote.value.copy(isEmpty = false)
                val existingNote = Note(
                    id = currentNote.value.id,
                    title = currentNote.value.title.trim(),
                    body = currentNote.value.body.trim(),
                    category = currentNote.value.category,
                    lastModificationDate = currentNote.value.lastModificationDate
                )
                noteUseCases.insertNote(existingNote)
            } else {
                currentNote.value = currentNote.value.copy(isEmpty = false)
                val newNote = Note(

                    title = currentNote.value.title.trim(),
                    body = currentNote.value.body.trim(),
                    category = currentNote.value.category,
                    lastModificationDate = currentNote.value.lastModificationDate
                )
                noteUseCases.insertNote(newNote)
            }
        } else {
            currentNote.value = currentNote.value.copy(isEmpty = true)
        }
    }

}
