package com.rzmmzdh.toro.feature_note.ui.home_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.NoteCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {
    var clearFilterButtonVisible = mutableStateOf(false)
        private set
    var notes = mutableStateOf(NoteListUiState())
        private set
    var searchQuery = mutableStateOf("")
        private set
    var showNoteDeletionNotification = mutableStateOf(false)
        private set
    var searchNotesJob: Job? = null
        private set
    var deletedNote: Note? = null
        private set
    var selectedCategory: MutableState<NoteCategory?> = mutableStateOf(null)
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
                    if (showNoteDeletionNotification.value) showNoteDeletionNotification.value =
                        !showNoteDeletionNotification.value
                    showNoteDeletionNotification.value = !showNoteDeletionNotification.value
                }
                is HomeScreenEvent.Search -> {
                    searchQuery.value = event.value
                    searchNotes(event.value)
                }
                is HomeScreenEvent.ClearSearchBox -> {
                    searchNotes("")

                }
                is HomeScreenEvent.UndoDeletedNote,
                -> {
                    if (showNoteDeletionNotification.value) {
                        showNoteDeletionNotification.value = !showNoteDeletionNotification.value
                    }
                    deletedNote?.let { noteUseCases.insertNote(it) }
                    deletedNote = null
                }
                HomeScreenEvent.NotificationDisplayed -> showNoteDeletionNotification.value =
                    !showNoteDeletionNotification.value
                is HomeScreenEvent.OnFilterItemSelected -> {
                    selectedCategory.value = event.filter
                    viewModelScope.launch {
                        noteUseCases.getNotesByCategory(event.filter).collectLatest {
                            notes.value = notes.value.copy(notes = it)
                        }
                    }
                    clearFilterButtonVisible.value = true
                }
                is HomeScreenEvent.OnClearFilter -> {
                    selectedCategory.value = null
                    clearFilterButtonVisible.value = !clearFilterButtonVisible.value
                    noteUseCases.getAllNotes().collectLatest {
                        notes.value = notes.value.copy(notes = it)
                    }
                }
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