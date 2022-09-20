package com.rzmmzdh.toro.feature_note.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import com.rzmmzdh.toro.feature_note.ui.core.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {
    init {
        viewModelScope.launch {
            repeat(12) {
                val sampleNote = Note(Random().nextInt(48), "عنوان", "متن", Date())
                noteUseCases.insertNote(sampleNote)
            }
            noteUseCases.getAllNotes().collectLatest {
                notes.value = notes.value.copy(notes = it)
            }
        }
    }

    var isDarkTheme = mutableStateOf(false)
        private set
    var notes = mutableStateOf(NoteListUiState())
        private set

    fun onEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeScreenEvent.AddNewNote -> {
                    val sampleNewNote = Note(Random().nextInt(), "عنوان", "متن", Date())
                    viewModelScope.launch {
                        noteUseCases.insertNote(sampleNewNote)
                    }
                }
                is HomeScreenEvent.DeleteAllNotes -> {
                    viewModelScope.launch {
                        noteUseCases.deleteAllNotes()
                    }

                }
                is HomeScreenEvent.DeleteNote -> noteUseCases.deleteNote(event.note)
                is HomeScreenEvent.ToggleTheme -> {
                    isDarkTheme.value = !isDarkTheme.value
                }
            }
        }
    }
}