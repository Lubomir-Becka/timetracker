package com.example.timetracker.utils

fun FormatTime(seconds: Long): String {
    val _hours = seconds / 3600;
    val _minutes = (seconds % 3600) / 60
    val _seconds = seconds % 60
    return "%02d:%02d:%02d".format(_hours , _minutes, _seconds)
}