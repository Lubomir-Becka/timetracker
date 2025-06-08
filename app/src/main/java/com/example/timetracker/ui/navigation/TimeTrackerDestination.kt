package com.example.timetracker.ui.navigation








import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a navigation destination in the TimeTracker app.
 *
 * @property route The route string for navigation.
 * @property label The label to display.
 * @property icon The icon to display.
 */
sealed class TimeTrackerDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Timer : TimeTrackerDestination("timer", "Pridať",  Icons.Filled.AddCircle)
    object Calendar : TimeTrackerDestination("calendar", "Kalendár", Icons.Filled.DateRange)

}