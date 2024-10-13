package com.example.hobbytracker.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}