package com.example.timetracker.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant


class Convertor {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimeStamp(value: String?): Instant? =
        value?.let { Instant.parse(it) }
    @TypeConverter
    fun instantToTimestamp(instant: Instant?): String? =
        instant?.toString()
}