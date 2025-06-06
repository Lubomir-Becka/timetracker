package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.timetracker.data.ActivityDatabase
import com.example.timetracker.repositories.ActivityRepository
import com.example.timetracker.viewmodel.TimerVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val context = LocalContext.current


    val db = remember {ActivityDatabase.getDatabase(context)}
    val repository = remember { ActivityRepository(db.activityDao())}

    val timerVM =TimerVM(repository)

    TimerScreen(timer = timerVM)

}