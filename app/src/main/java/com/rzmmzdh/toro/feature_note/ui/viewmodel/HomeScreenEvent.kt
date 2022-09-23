package com.rzmmzdh.toro.feature_note.ui.viewmodel

import com.rzmmzdh.toro.feature_note.domain.model.Note


sealed class HomeScreenEvent {
    data class DeleteNote(val note: Note) : HomeScreenEvent()
}
