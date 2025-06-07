package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.timetracker.data.ActivityDatabase
import com.example.timetracker.repositories.ActivityRepository
import com.example.timetracker.ui.navigation.TimeTrackerDestination
import com.example.timetracker.viewmodel.CalendarVM
import com.example.timetracker.viewmodel.TimerVM
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetracker.viewmodel.CalendarVMFactory
import com.example.timetracker.viewmodel.TimerVMFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val db = remember {ActivityDatabase.getDatabase(context)}
    val repository = remember { ActivityRepository(db.activityDao())}

    val timerVMFactory = remember { TimerVMFactory(repository) }
    val calendarVMFactory = remember { CalendarVMFactory(repository) }
    val timerVM: TimerVM = viewModel(factory = timerVMFactory)
    val calendarVM: CalendarVM = viewModel(factory = calendarVMFactory)

    val bottomNavScreens = listOf(
        TimeTrackerDestination.Timer,
        TimeTrackerDestination.Calendar

    )

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavScreens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TimeTrackerDestination.Timer.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TimeTrackerDestination.Timer.route) {
                TimerScreen(timer = timerVM)
            }
            composable(TimeTrackerDestination.Calendar.route) {
                CalendarScreen(calendarVM = calendarVM)
            }
        }
    }
}