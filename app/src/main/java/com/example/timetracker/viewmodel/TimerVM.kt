package com.example.timetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.timetracker.data.ActivityEntry
import androidx.lifecycle.viewModelScope
import com.example.timetracker.repositories.ActivityRepository
import kotlinx.coroutines.launch


class TimerVM(private val repository: ActivityRepository) : ViewModel(){
    var name: String = ""
    var start: Long = 0L
    var duration: Long = 0L

    fun saveActivity() {
        if (name.isNotBlank() && duration > 0) {
            val activity = ActivityEntry(name = name, start = start, duration = duration)
            viewModelScope.launch { repository.insert(activity = activity)  }
        }
    }
}