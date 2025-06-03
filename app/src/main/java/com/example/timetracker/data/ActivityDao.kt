package com.example.timetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

@Dao
interface ActivityDao {
    @Insert
    suspend fun insert(activity: ActivityEntry)

    @Update
    suspend fun update(activity: ActivityEntry)

    @Delete
    suspend fun delete(activity: ActivityEntry)

}