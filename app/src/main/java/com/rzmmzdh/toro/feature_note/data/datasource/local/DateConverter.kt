package com.rzmmzdh.toro.feature_note.data.datasource.local

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDateToTimeStamp(date: Date): Long {
        return date.time.toLong()
    }

    @TypeConverter
    fun fromTimeStampToDate(timeStamp: Long): Date {
        return Date(timeStamp)
    }
}