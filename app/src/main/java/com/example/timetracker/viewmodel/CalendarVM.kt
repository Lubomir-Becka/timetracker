package com.example.timetracker.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetracker.data.ActivityEntry
import com.example.timetracker.repositories.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

private var lastFrom: Instant? = null
private var lastTo: Instant? = null
/**
 * ViewModel Calendar.
 */
class CalendarVM (private val repository: ActivityRepository) : ViewModel(){

    private val _activities = MutableStateFlow<List<ActivityEntry>>(emptyList())
    val activities: StateFlow<List<ActivityEntry>> = _activities

    /**
     * Loads activities for the specified time interval.
     * @param from Start of the interval.
     * @param to End of the interval.
     */
    fun loadActivitiesForInterval(from: Instant, to: Instant) {
        lastFrom = from
        lastTo = to
        viewModelScope.launch {
            _activities.value = repository.getActivitiesBetween(from, to)
        }
    }

    /**
     * Deletes the given activity from the database and reloads activities.
     * @param activity The selected activity to delete.
     */
    fun deleteActivity(activity: ActivityEntry) {
        viewModelScope.launch {
            repository.deleteActivity(activity)
        }
        reloadLast()
    }

    /**
     * Updates the activity name and clears the error message if the name is not blank.
     * @param newName The new name of the activity.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateActivity(activity: ActivityEntry, newName: String) {
        if(newName.isNotBlank()) {
            viewModelScope.launch {
                repository.updateActivity(activity.copy(name = newName))
            }
            reloadLast()
        }
    }
    /**
     * Loads activities for the entire week starting from [weekStart].
     * @param weekStart The first day of the week.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadActivitiesForWeek(weekStart: LocalDate) {
        val from = weekStart.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val to = weekStart.plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()
        loadActivitiesForInterval(from = from, to = to)
    }

    /**
     * Reloads last activities.
     */
    fun reloadLast() {
        if (lastFrom != null && lastTo != null)
                loadActivitiesForInterval(lastFrom!!, lastTo!!)
    }
}