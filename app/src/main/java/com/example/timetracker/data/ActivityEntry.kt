package com.example.timetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntry (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val start: Long,
    val duration: Long
)