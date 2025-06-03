package com.example.timetracker.repositories

import com.example.timetracker.data.ActivityDao
import com.example.timetracker.data.ActivityEntry


class Repository(private val activityDao: ActivityDao){
    suspend fun insert(activity: ActivityEntry) {
        activityDao.insert(activity)
    }

    suspend fun updateActivity(activity: ActivityEntry) {
        activityDao.update(activity)
    }

    suspend fun deleteActivity(activity: ActivityEntry) {
        activityDao.delete(activity)
    }

}