package com.rzmmzdh.toro.feature_note.ui.home_screen

import com.rzmmzdh.toro.feature_note.domain.model.Note


sealed class HomeScreenEvent {
    data class EditNote(val noteId: Int) : HomeScreenEvent()
    data class DeleteNote(val note: Note) : HomeScreenEvent()
    data class OnSearch(val value: String) : HomeScreenEvent()
    object UndoDeletedNote : HomeScreenEvent()
    object ClearSearchBox : HomeScreenEvent()
    object NotificationDisplayed : HomeScreenEvent()
}
