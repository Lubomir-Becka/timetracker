package com.example.timetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetracker.data.ActivityEntry
import com.example.timetracker.repositories.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class CalendarVM (private val repository: ActivityRepository) : ViewModel(){

    private val _activities = MutableStateFlow<List<ActivityEntry>>(emptyList())
    val activities: StateFlow<List<ActivityEntry>> = _activities

    fun loadActivitiesForInterval(from: Instant, to: Instant) {
        viewModelScope.launch {
            _activities.value = repository.getActivitiesBetween(from, to)
        }
    }
}