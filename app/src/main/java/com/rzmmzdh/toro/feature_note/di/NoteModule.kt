package com.rzmmzdh.toro.feature_note.di

import android.app.Application
import androidx.room.Room
import com.rzmmzdh.toro.feature_note.data.datasource.local.NoteDao
import com.rzmmzdh.toro.feature_note.data.datasource.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {
    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Application): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_database.db"
        ).build()
    }
}