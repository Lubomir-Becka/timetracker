package com.example.timetracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TimeTrackerDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Timer : TimeTrackerDestination("timer", "Časovač", Icons.Filled.Info )
    object Calendar : TimeTrackerDestination("calendar", "Kalendár", Icons.Filled.DateRange)
}