package com.example.timetracker.repositories

import com.example.timetracker.data.ActivityDao
import com.example.timetracker.data.ActivityEntry
import java.time.Instant


class ActivityRepository(private val activityDao: ActivityDao){
    suspend fun insert(activity: ActivityEntry) {
        activityDao.insert(activity)
    }

    suspend fun updateActivity(activity: ActivityEntry) {
        activityDao.update(activity)
    }

    suspend fun deleteActivity(activity: ActivityEntry) {
        activityDao.delete(activity)
    }
    suspend fun getAll(){
        activityDao.getAll();
    }

    suspend fun getActivitiesBetween(from: Instant, to: Instant){
        activityDao.getActivitesBetween(from = from, to = to)
    }
}