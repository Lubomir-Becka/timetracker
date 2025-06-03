package com.example.timetracker.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [ActivityEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}