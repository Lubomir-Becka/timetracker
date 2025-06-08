package com.example.timetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetracker.repositories.ActivityRepository
/**
 * Factory for creating instances of [CalendarVM] with a provided [ActivityRepository].
 *
 * @property repository The repository used by the CalendarVM.
 */
class CalendarVMFactory (
    private val repository: ActivityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}