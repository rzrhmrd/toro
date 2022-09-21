package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class Converters {
    @TypeConverter
    fun longToInstant(value: Long): Instant {
        return Instant.fromEpochMilliseconds(value)
    }

    @TypeConverter
    fun instantToLong(instant: Instant): Long {
        return instant.toEpochMilliseconds()
    }

}