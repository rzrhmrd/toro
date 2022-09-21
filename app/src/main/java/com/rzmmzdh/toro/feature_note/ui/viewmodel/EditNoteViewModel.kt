package com.rzmmzdh.toro.feature_note.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {
    var titleText = mutableStateOf("")
        private set
    var bodyText = mutableStateOf("")
        private set

    fun onEvent(event: EditNoteEvent) {
        viewModelScope.launch {
            when (event) {
                is EditNoteEvent.SaveNote -> {
                    val note = Note(
                        id = Random.nextInt(),
                        title = titleText.value,
                        body = bodyText.value,
                        lastModificationDate = Instant.fromEpochMilliseconds(1)
                    )
                    noteUseCases.insertNote(note)
                }
                is EditNoteEvent.OnTitleChanged -> titleText.value = event.value
                is EditNoteEvent.OnBodyChanged -> bodyText.value = event.value
            }
        }

    }

}
