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

class CalendarVM (private val repository: ActivityRepository) : ViewModel(){

    private val _activities = MutableStateFlow<List<ActivityEntry>>(emptyList())
    val activities: StateFlow<List<ActivityEntry>> = _activities

    fun loadActivitiesForInterval(from: Instant, to: Instant) {
        viewModelScope.launch {
            _activities.value = repository.getActivitiesBetween(from, to)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadActivitiesForWeek(weekStart: LocalDate) {
        val from = weekStart.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val to = weekStart.plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()
        loadActivitiesForInterval( from = from, to = to)
    }
}