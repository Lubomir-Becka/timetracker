package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.timetracker.utils.FormatTime
import com.example.timetracker.viewmodel.CalendarVM
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(calendarVM: CalendarVM) {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }


    LaunchedEffect(currentDate) {
        val from = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val to = currentDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
        calendarVM.loadActivitiesForInterval(from, to)
    }

    val activities by calendarVM.activities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { currentDate = currentDate.minusDays(1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Predchádzajúci deň")
            }
            Text(
                text = currentDate.format(DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy")),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = { currentDate = currentDate.plusDays(1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Nasledujúci deň")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(24) { hour ->
                val hourActivities = activities.filter { activity ->
                    val activityHour = Instant.ofEpochSecond(activity.start.epochSecond)
                        .atZone(ZoneId.systemDefault())
                        .hour
                    activityHour == hour
                }
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "%02d:00".format(hour),
                        modifier = Modifier.width(56.dp),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        hourActivities.forEach { activity ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = activity.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = FormatTime(activity.duration),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 2.dp),
                    thickness = 1.dp,
                    color = Color.LightGray)
            }
        }
    }
}

