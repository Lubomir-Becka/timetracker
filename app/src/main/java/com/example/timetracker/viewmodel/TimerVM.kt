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
    var nameError by mutableStateOf<String?>(null)


    fun updateName(newName: String) {
        name = newName
        if (nameError != null && newName.isNotBlank()) {
            nameError = null // clear error when user starts typing
        }
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
        timerJob?.cancel()
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
        } else {
            nameError = "Vyplnte názov aktivity!"
        }
    }

    fun reset() {
        isRunning = false
        duration = 0L
        start = Instant.EPOCH
        timerJob?.cancel()
    }
    fun formatTime(seconds: Long): String {
        val _hours = seconds / 3600;
        val _minutes = (seconds % 3600) / 60
        val _seconds = seconds % 60
        return "%02d:%02d:%02d".format(_hours , _minutes, _seconds)
    }
}