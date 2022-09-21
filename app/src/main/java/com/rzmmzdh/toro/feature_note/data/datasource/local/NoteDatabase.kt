package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}