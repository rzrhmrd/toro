package com.rzmmzdh.toro.feature_note.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {
    var titleText = mutableStateOf("")
        private set
    var bodyText = mutableStateOf("")
        private set
    var openDialog = mutableStateOf(false)
        private set
    val errorMessages =
        listOf("هممم... فکر نکنم بشه فکر خالی رو ذخیره کرد.",
            "خالی، خالی، خالی...",
            "از مترادف های خالی، تُهی و خلوت می باشند.")

    fun onEvent(event: EditNoteEvent) {
        viewModelScope.launch {
            when (event) {
                is EditNoteEvent.SaveNote -> {
                    if (titleText.value.isNotBlank() || bodyText.value.isNotBlank()) {
                        val note = Note(
                            title = titleText.value,
                            body = bodyText.value,
                            lastModificationDate = Clock.System.now()
                        )
                        noteUseCases.insertNote(note)
                    }
                }
                is EditNoteEvent.OnTitleChanged -> titleText.value = event.value
                is EditNoteEvent.OnBodyChanged -> bodyText.value = event.value
                is EditNoteEvent.OpenDialog -> openDialog.value = !openDialog.value
            }
        }

    }

}
