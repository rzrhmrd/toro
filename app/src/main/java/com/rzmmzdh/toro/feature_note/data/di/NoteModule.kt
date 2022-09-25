package com.rzmmzdh.toro.feature_note.data.di

import android.app.Application
import androidx.room.Room
import com.rzmmzdh.toro.feature_note.data.datasource.local.LocalDataSource
import com.rzmmzdh.toro.feature_note.data.datasource.local.NoteDao
import com.rzmmzdh.toro.feature_note.data.datasource.local.NoteDatabase
import com.rzmmzdh.toro.feature_note.data.repository.NoteRepositoryImpl
import com.rzmmzdh.toro.feature_note.domain.repository.NoteRepository
import com.rzmmzdh.toro.feature_note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideNoteDatabase(context: Application): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(noteDao: NoteDao): LocalDataSource {
        return LocalDataSource(noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(localDataSource: LocalDataSource): NoteRepository {
        return NoteRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNote = GetNote(repository),
            getAllNotes = GetAllNotes(repository),
            insertNote = InsertNote(repository),
            deleteNote = DeleteNote(repository),
            deleteAllNotes = DeleteAllNotes(repository),
            searchNotes = SearchNotes(repository)
        )
    }
}
