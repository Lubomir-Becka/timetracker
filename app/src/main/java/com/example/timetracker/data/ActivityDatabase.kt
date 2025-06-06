package com.example.timetracker.data


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ActivityEntry::class], version = 1)
abstract class ActivityDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}