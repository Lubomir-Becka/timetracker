package com.example.timetracker.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.timetracker.data.ActivityEntry
import androidx.lifecycle.viewModelScope
import com.example.timetracker.repositories.ActivityRepository
import kotlinx.coroutines.launch
import java.time.Instant
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

private var timerJob: Job? = null
@RequiresApi(Build.VERSION_CODES.O)
class TimerVM(private val repository: ActivityRepository) : ViewModel(){

    var name by mutableStateOf("")
    var duration by mutableStateOf(0L)
    var start by mutableStateOf(Instant.EPOCH)
    var isRunning by mutableStateOf(false)

    fun updateName(newName: String) {
        name = newName
    }

    fun start() {
        if (!isRunning) {
            start = Instant.now()
            isRunning = true
            timerJob = viewModelScope.launch {
                while (isRunning) {
                    duration = Instant.now().epochSecond - start.epochSecond
                    delay(1000)
                }
            }
        }
    }

    fun stop() {
        if (name.isNotBlank() && isRunning) {
            duration = Instant.now().epochSecond - start.epochSecond
            isRunning = false
            timerJob?.cancel()
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
        timerJob?.cancel()
    }
}