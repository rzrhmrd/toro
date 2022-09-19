package com.rzmmzdh.toro.feature_note.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import com.rzmmzdh.toro.feature_note.ui.HomeScreenEvent
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
                noteList.value = noteList.value.copy(notes = it)
            }
        }
    }


    var noteList = mutableStateOf(NoteListUiState())
        private set

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.AddNewNote -> {
                val sampleNewNote = Note(Random().nextInt(), "عنوان", "متن", Date())
                viewModelScope.launch {
                    noteUseCases.insertNote(sampleNewNote)
                }
            }
            HomeScreenEvent.DeleteAllNotes -> {
                viewModelScope.launch {
                    noteUseCases.deleteAllNotes()
                }

            }
        }
    }
}