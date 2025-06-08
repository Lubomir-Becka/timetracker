package com.example.timetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import java.time.Instant
/**
 * Data Access Object  for accessing activity entries in the Room database.
 */
@Dao
interface ActivityDao {
    /**
     * Inserts a new activity entry into the database.
     * @param activity The activity to insert.
     */
    @Insert
    suspend fun insert(activity: ActivityEntry)
    /**
     * Updates an existing activity in the database.
     * @param activity The activity to update.
     */
    @Update
    suspend fun update(activity: ActivityEntry)
    /**
     * Deletes an activity from the database.
     * @param activity The activity to delete.
     */
    @Delete
    suspend fun delete(activity: ActivityEntry)
    /**
     * Retrieves all activity entries from the database, ordered by start time descending.
     * @return A list of all activity entries.
     */
    @Query("select * from activities order by start desc")
    suspend fun getAll(): List<ActivityEntry>
    /**
     * Retrieves activity entries that started between the specified time interval, ordered by start time descending.
     * @param from The start of the interval.
     * @param to The end of the interval.
     * @return A list of activity entries in the interval.
     */
    @Query("select * from activities where start between :from and :to order by start desc")
    suspend fun getActivitesBetween(from: Instant, to: Instant): List<ActivityEntry>
}