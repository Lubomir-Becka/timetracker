package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.timetracker.viewmodel.CalendarVM
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.getValue
import com.example.timetracker.utils.FormatSecToHMS
import com.example.timetracker.utils.FormatTimeToHM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyScreen(calendarVM: CalendarVM) {
    val today = LocalDate.now()
    val weekStart = today.with(java.time.DayOfWeek.MONDAY)
    val daysOfWeek = (0..6).map { weekStart.plusDays(it.toLong()) }


    LaunchedEffect(weekStart) {
        calendarVM.loadActivitiesForWeek(weekStart)
    }

    val activities by calendarVM.activities.collectAsState()

    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            val dayStart = day.atStartOfDay(ZoneId.systemDefault()).toInstant()
            val dayEnd = day.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
            val dayActivities = activities.filter { activity ->
                val activityStart = activity.start
                val activityEnd = activity.start.plusSeconds(activity.duration)
                activityStart < dayEnd && activityEnd > dayStart
            }

            Column(
                modifier = Modifier.weight(1f).padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day.dayOfWeek.name.take(3),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = day.format(DateTimeFormatter.ofPattern("d.M.")),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(4.dp))
                if (dayActivities.isEmpty()) {
                    Text("–", color = Color.LightGray)
                } else {
                    dayActivities.forEach { activity ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 2.dp)
                        ) {
                            Column(Modifier.padding(6.dp)) {
                                Text(
                                    text = activity.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Row() {
                                    Text(
                                        text = FormatTimeToHM(activity.start),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        text = FormatSecToHMS(activity.duration),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}