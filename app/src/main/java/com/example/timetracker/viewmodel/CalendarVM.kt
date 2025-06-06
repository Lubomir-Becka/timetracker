package com.example.timetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetracker.repositories.ActivityRepository
import kotlinx.coroutines.launch

class CalendarVM (private val repository: ActivityRepository) : ViewModel(){

    fun insertIntoCalendar(from: Long, to: Long) {
        viewModelScope.launch {
                
        }
    }
}