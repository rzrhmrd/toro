package com.rzmmzdh.toro.feature_note.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {
    var notes = mutableStateOf(NoteListUiState())
        private set

    init {
        viewModelScope.launch {
            noteUseCases.getAllNotes().collectLatest {
                notes.value = NoteListUiState(notes = it)
            }
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeScreenEvent.DeleteNote -> noteUseCases.deleteNote(event.note)
            }
        }
    }
}