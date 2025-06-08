package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.example.timetracker.data.ActivityEntry
import com.example.timetracker.utils.formatSecToHMS
import com.example.timetracker.utils.formatTimeToHM
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyScreen(calendarVM: CalendarVM) {
    var weekStart by remember { mutableStateOf(LocalDate.now().with(java.time.DayOfWeek.MONDAY)) }
    val daysOfWeek = (0..6).map { weekStart.plusDays(it.toLong()) }
    Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { weekStart = weekStart.minusWeeks(1) }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Predchádzajúci t")
        }
        Text(
            text = "${weekStart.format(DateTimeFormatter.ofPattern("d.M."))} - ${weekStart.plusDays(6).format(DateTimeFormatter.ofPattern("d.M.yyyy"))}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        if (weekStart.plusDays(6) < LocalDate.now()) {
            IconButton(onClick = { weekStart = weekStart.plusWeeks(1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Nasledujúci týždeň")
            }
        }
    }

    LaunchedEffect(weekStart) {
        calendarVM.loadActivitiesForWeek(weekStart)
    }

    val activities by calendarVM.activities.collectAsState()
    val activitiesByDay: Map<LocalDate, List<ActivityEntry>> = activities.groupBy { activity ->
        activity.start.atZone(ZoneId.systemDefault()).toLocalDate()
    }
    LazyColumn (
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth())
            {
                daysOfWeek.forEach { day ->
                    val dayActivities = activitiesByDay[day] ?: emptyList()
                    Column(
                        modifier = Modifier.weight(1f).padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("sk")),
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
                                        containerColor = MaterialTheme.colorScheme.primary.copy(
                                            alpha = 0.08f
                                        )
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
                                        Row {
                                            Text(
                                                text = formatTimeToHM(activity.start),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(1f)
                                            )

                                            Text(
                                                text = formatSecToHMS(activity.duration),
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
    }
    }
}