package com.rzmmzdh.toro.feature_note.ui.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var notes = mutableStateOf(NoteListUiState())
        private set
    var searchQuery = mutableStateOf("")
        private set
    var showNoteDeletionNotification = mutableStateOf(false)
        private set
    private var searchNotesJob: Job? = null
    var deletedNote: Note? = null
        private set

    init {
        viewModelScope.launch {
            noteUseCases.getAllNotes().collectLatest {
                notes.value = notes.value.copy(notes = it)
            }
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeScreenEvent.DeleteNote -> {
                    deletedNote = event.note
                    noteUseCases.deleteNote(event.note)
                    showNoteDeletionNotification.value = !showNoteDeletionNotification.value
                }
                is HomeScreenEvent.Search -> searchNotes(event.value)
                is HomeScreenEvent.ClearSearchBox -> searchNotes("")
                is HomeScreenEvent.EditNote -> TODO()
                is HomeScreenEvent.UndoDeletedNote -> {
                    deletedNote?.let { noteUseCases.insertNote(it) }
                    deletedNote = null
                    showNoteDeletionNotification.value = !showNoteDeletionNotification.value
                }
                HomeScreenEvent.NotificationDisplayed -> showNoteDeletionNotification.value =
                    !showNoteDeletionNotification.value
            }
        }
    }

    private fun searchNotes(query: String) {
        searchQuery.value = query
        searchNotesJob?.cancel()
        searchNotesJob = viewModelScope.launch {
            delay(500L)
            noteUseCases.searchNotes(query).collectLatest {
                notes.value = notes.value.copy(notes = it)
            }

        }
    }
}