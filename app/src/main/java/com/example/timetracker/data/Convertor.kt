package com.example.timetracker.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant

/**
 * Type converters for converting between Instant and String for Room database.
 */
class Convertor {
    /**
     * Converts a String timestamp to an Instant.
     * @param value The string representation of the timestamp.
     * @return The corresponding Instant.
     */
    //inspiracia https://developer.android.com/training/data-storage/room/referencing-data#type-converters
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimeStamp(value: String?): Instant? =
        value?.let { Instant.parse(it) }
    /**
     * Converts an Instant to its String representation.
     * @param instant The Instant to convert.
     * @return The string representation of the Instant.
     */
    @TypeConverter
    fun instantToTimestamp(instant: Instant?): String? =
        instant?.toString()
}