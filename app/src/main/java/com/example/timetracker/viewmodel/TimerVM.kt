package com.example.timetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.timetracker.data.ActivityEntry
import androidx.lifecycle.viewModelScope
import com.example.timetracker.repositories.ActivityRepository
import kotlinx.coroutines.launch
import java.time.Instant


class TimerVM(private val repository: ActivityRepository) : ViewModel(){
    var name: String = ""
    var start: Instant = Instant.now()
    var duration: Long = 0L

    fun saveActivity() {
        if (name.isNotBlank() && duration > 0) {
            val activity = ActivityEntry(name = name, start = start, duration = duration)
            viewModelScope.launch { repository.insert(activity = activity)  }
        }
    }
}