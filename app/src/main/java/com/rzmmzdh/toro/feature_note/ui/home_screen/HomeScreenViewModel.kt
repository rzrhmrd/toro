package com.rzmmzdh.toro.feature_note.ui.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var searchJob: Job? = null

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
                is HomeScreenEvent.DeleteNote -> noteUseCases.deleteNote(event.note)
                is HomeScreenEvent.OnSearch -> searchNotes(event.value)
                is HomeScreenEvent.ClearSearchBox -> searchNotes("")
                is HomeScreenEvent.EditNote -> TODO()
            }
        }
    }

    private fun searchNotes(query: String) {
        searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            noteUseCases.searchNotes(query).collectLatest {
                notes.value = notes.value.copy(notes = it)
            }

        }
    }
}