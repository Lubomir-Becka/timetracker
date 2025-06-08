package com.example.timetracker.ui.screens

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.timetracker.viewmodel.CalendarVM


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(calendarVM: CalendarVM) {
    if (isLandscape()) {
        WeeklyScreen(calendarVM)
    } else {
        DailyScreen(calendarVM)
    }
}
@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

