package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.core.util.TimeUtils.formatDuration


import com.example.timetracker.viewmodel.TimerVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun TimerScreen(timer: TimerVM) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = timer.name,
            onValueChange = { timer.updateName(it) },
            label = { Text("Názov aktivity") },
            modifier = Modifier.padding(top = 32.dp).fillMaxWidth()
        )

        Text(
            text = formatTime(timer.duration),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(top = 32.dp, bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {
                    if (timer.isRunning) timer.stop() else timer.start()
                }
            ) {
                Text(if (timer.isRunning) "Stop" else "Štart")
            }

            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { timer.reset() }
            ) {
                Text("Reštart")
            }
        }
    }
}

fun formatTime(seconds: Long): String {
    val _hours = seconds / 3600;
    val _minutes = (seconds % 3600) / 60
    val _seconds = seconds % 60
    return "%02d:%02d:%02d".format(_hours , _minutes, _seconds)
}