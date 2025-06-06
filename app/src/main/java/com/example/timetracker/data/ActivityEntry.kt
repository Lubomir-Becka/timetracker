package com.example.timetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "activities")
data class ActivityEntry (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val start: Instant,
    val duration: Long
)