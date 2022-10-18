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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val noteUseCases: NoteUseCases) :
    ViewModel() {
    var search = mutableStateOf(SearchUiState())
        private set
    var searchNotesJob: Job? = null
        private set
    var clearFilterButtonVisible = mutableStateOf(false)
        private set
    var selectedCategory: MutableState<NoteCategory?> = mutableStateOf(null)
        private set
    var notes = mutableStateOf(NoteListUiState())
        private set
    var deletedNote: Note? = null
        private set
    var showNoteDeleteNotification = mutableStateOf(false)
        private set

    init {
        getAllNotes()
        resetDeletedNote()
    }

    private fun resetDeletedNote() {
        viewModelScope.launch {
            deletedNote?.let {
                deletedNote = null
            }
            deletedNote = null
            showNoteDeleteNotification.value = false

        }
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            noteUseCases.getAllNotes().collectLatest {
                notes.value = notes.value.copy(notes = it)
            }
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeScreenEvent.OnNoteDelete -> {
                    deleteNote(event)
                }
                is HomeScreenEvent.OnSearch -> {
                    if (!search.value.isClearSearchIconVisible) {
                        search.value = search.value.copy(isClearSearchIconVisible = true)
                    }
                    searchNotes(event.query)
                    search.value = search.value.copy(searchQuery = event.query)
                }
                is HomeScreenEvent.OnClearSearchBox -> {
                    searchNotes("")
                    search.value = search.value.copy(isClearSearchIconVisible = false)

                }
                is HomeScreenEvent.OnUndoNoteDelete,
                -> {
                    if (showNoteDeleteNotification.value) {
                        showNoteDeleteNotification.value = !showNoteDeleteNotification.value
                    }
                    deletedNote?.let { noteUseCases.insertNote(it) }
                    deletedNote = null
                }
                HomeScreenEvent.NotificationDisplayed -> {
                    showNoteDeleteNotification.value =
                        !showNoteDeleteNotification.value
                }
                is HomeScreenEvent.OnFilterItemSelect -> {
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

    private suspend fun deleteNote(
        event: HomeScreenEvent.OnNoteDelete,
    ) {
        deletedNote = event.note
        noteUseCases.deleteNote(event.note)
        if (showNoteDeleteNotification.value) showNoteDeleteNotification.value =
            !showNoteDeleteNotification.value
        showNoteDeleteNotification.value = !showNoteDeleteNotification.value
    }

    private fun searchNotes(query: String) {
        search.value = search.value.copy(searchQuery = query)
        searchNotesJob?.cancel()
        searchNotesJob = viewModelScope.launch {
            delay(500L)
            noteUseCases.searchNotes(query).collectLatest {
                notes.value = notes.value.copy(notes = it)
            }

        }
    }
}