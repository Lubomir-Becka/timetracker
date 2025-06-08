package com.example.timetracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun FormatSecToHMS(seconds: Long): String {
    val _hours = seconds / 3600;
    val _minutes = (seconds % 3600) / 60
    val _seconds = seconds % 60
    return "%02d:%02d:%02d".format(_hours , _minutes, _seconds)
}

@RequiresApi(Build.VERSION_CODES.O)
fun FormatTimeToHM (instant: Instant) : String {
    val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()
    return localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}