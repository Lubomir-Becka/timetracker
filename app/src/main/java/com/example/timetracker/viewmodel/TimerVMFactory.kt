package com.example.timetracker.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetracker.repositories.ActivityRepository

class TimerVMFactory (
    private val repository: ActivityRepository
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}