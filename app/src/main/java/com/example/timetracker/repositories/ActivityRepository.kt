package com.example.timetracker.repositories

import com.example.timetracker.data.ActivityDao
import com.example.timetracker.data.ActivityEntry
import java.time.Instant

/**
 * Repository for managing activity data.
 * Provides an abstraction layer between the ViewModel and the data source (Room database).
 *
 * @property activityDao The DAO for accessing activity data.
 */
class ActivityRepository(private val activityDao: ActivityDao){
    /**
     * Inserts a new activity into the database.
     * @param activity The activity to insert.
     */
    suspend fun insert(activity: ActivityEntry) {
        activityDao.insert(activity)
    }
    /**
     * Updates an existing activity in the database.
     * @param activity The activity to update.
     */
    suspend fun updateActivity(activity: ActivityEntry) {
        activityDao.update(activity)
    }
    /**
     * Deletes an activity from the database.
     * @param activity The activity to delete.
     */
    suspend fun deleteActivity(activity: ActivityEntry) {
        activityDao.delete(activity)
    }

    /**
     * Retrieves all activities from the database.
     * @return A list of all activities.
     */
    suspend fun getAll():  List<ActivityEntry>{
        return activityDao.getAll();
    }

    /**
     * Returns activities between the specified time interval.
     * @param from The start of the interval .
     * @param to The end of the interval .
     * @return A list of activities in the interval.
     */
    suspend fun getActivitiesBetween(from: Instant, to: Instant): List<ActivityEntry>{
        return activityDao.getActivitesBetween(from = from, to = to)
    }
}