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
import java.time.LocalDate
import java.time.ZoneId


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
            nameError = null
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

    fun insertSampleData() {
        viewModelScope.launch {
            repository.insert(
                ActivityEntry(
                    name = "Sample Activity",
                    start = Instant.now(),
                    duration = 1600
                )
            )
            repository.insert(
                ActivityEntry(
                    name = "Sample Activity 1",
                    start = Instant.now().plusSeconds(3600),
                    duration = 3600
                )
            )
            repository.insert(
                ActivityEntry(
                    name = "Sample Activity 2",
                    start = Instant.now().minusSeconds(3800),
                    duration = 3660
                )
            )
            repository.insert(
                ActivityEntry(
                    name = "Sample Activity 3",
                    start = LocalDate.now().minusDays(1).atTime(23, 30).atZone(ZoneId.systemDefault()).toInstant(),
                    duration = 3660
                )
            )
        }
    }
}