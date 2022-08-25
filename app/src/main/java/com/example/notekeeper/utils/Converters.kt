package com.example.notekeeper.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun stringToDate(value: String): LocalDateTime {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ISO_DATE_TIME)
    }
}