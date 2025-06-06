package com.example.timetracker.viewmodel

import android.os.Build
import android.os.health.TimerStat
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.timetracker.data.ActivityEntry
import androidx.lifecycle.viewModelScope
import com.example.timetracker.repositories.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class TimerVM(private val repository: ActivityRepository) : ViewModel(){

    var name: String = ""
    var duration: Long = 0L
    var start: Instant = Instant.EPOCH
    var isRunning: Boolean = false

    fun setName(newName: String) {
        name = newName
    }

    fun start() {
        start = Instant.now()
        isRunning = true
    }

    fun stop() {
        if (name.isNotBlank() && isRunning) {
            duration = Instant.now().epochSecond - start.epochSecond
            isRunning = false
            val activity = ActivityEntry(
                name = name,
                duration = duration,
                start = start
            )
            viewModelScope.launch {
                repository.insert(activity)
            }
        }
    }

    fun reset() {
        isRunning = false
        duration = 0L
        start = Instant.EPOCH
    }
}