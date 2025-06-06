package com.example.timetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import java.time.Instant

@Dao
interface ActivityDao {
    @Insert
    suspend fun insert(activity: ActivityEntry)

    @Update
    suspend fun update(activity: ActivityEntry)

    @Delete
    suspend fun delete(activity: ActivityEntry)

    @Query("select * from activities order by start desc")
    suspend fun getAll(): List<ActivityEntry>

    @Query("select * from activities where start between :from and :to order by start desc")
    suspend fun getActivitesBetween(from: Instant, to: Instant)

}