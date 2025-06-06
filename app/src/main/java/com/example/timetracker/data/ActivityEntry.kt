package com.example.timetracker.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "activities")
data class ActivityEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val start: Instant = Instant.EPOCH,
    val duration: Long
)