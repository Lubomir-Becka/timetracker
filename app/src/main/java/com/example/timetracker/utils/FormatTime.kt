package com.example.timetracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Converts a number of seconds to HH:mm:ss.
 * @param seconds Number of seconds.
 * @return String in the format HH:mm:ss.
 */
fun formatSecToHMS(seconds: Long): String {
    val _hours = seconds / 3600;
    val _minutes = (seconds % 3600) / 60
    val _seconds = seconds % 60
    return "%02d:%02d:%02d".format(_hours , _minutes, _seconds)
}
/**
 * Formats the time from Instant to HH:mm.
 * @param instant The time value as Instant.
 * @return String in the format HH:mm.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun formatTimeToHM (instant: Instant) : String {
    val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()
    return localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}