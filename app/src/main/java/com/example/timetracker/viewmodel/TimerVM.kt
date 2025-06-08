package com.example.timetracker.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.timetracker.data.ActivityEntry
import androidx.lifecycle.viewModelScope
import com.example.timetracker.repositories.ActivityRepository
import com.example.timetracker.worker.cancelNotification
import com.example.timetracker.worker.startNotification
import kotlinx.coroutines.launch
import java.time.Instant
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.ZoneId


private var timerJob: Job? = null
/**
 * ViewModel for timer.
 */
@RequiresApi(Build.VERSION_CODES.O)
class TimerVM(private val repository: ActivityRepository) : ViewModel(){

    var name by mutableStateOf("")
    var duration by mutableLongStateOf(0L)
    var start by mutableStateOf(Instant.EPOCH)
    var isRunning by mutableStateOf(false)
    var nameError by mutableStateOf<String?>(null)

    /**
     * Updates the activity name and clears the error if the name is not blank.
     * @param newName The new name.
     */
    fun updateName(newName: String) {
        name = newName
        if (nameError != null && newName.isNotBlank()) {
            nameError = null
        }
    }
    /**
     * Starts timer if activity name is provided.
     * If activity name is blank, sets an error message.
     */
    fun start(context: Context) {
        if (!isRunning && name.isNotBlank()) {
            onTimerStart(context, name)
            start = Instant.now()
            isRunning = true
            timerJob = viewModelScope.launch {
                while (isRunning) {
                    duration = Instant.now().epochSecond - start.epochSecond
                    delay(1000)
                }
            }
        } else {
            nameError = "Vyplnte názov aktivity!"
        }
    }

    /**
     *  Stops timer and inserts activity into database.
     */
    fun stop(context: Context) {
        duration = Instant.now().epochSecond - start.epochSecond
        onTimerStop(context)
        val activity = ActivityEntry(
            name = name,
            duration = duration,
            start = start
        )
        viewModelScope.launch {
            repository.insert(activity)
        }
        reset()

    }

    /**
     * Resets timer.
     */
    fun reset() {
        isRunning = false
        duration = 0L
        start = Instant.EPOCH
        timerJob?.cancel()
    }

    fun onTimerStart(context: Context, activityName: String) {
        startNotification(context, activityName)
    }

    fun onTimerStop(context: Context) {
        cancelNotification(context)
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