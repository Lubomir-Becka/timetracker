package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text


import com.example.timetracker.viewmodel.TimerVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun TimerScreen(timer: TimerVM) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = timer.name,
            onValueChange = { timer.updateName(it) },
            label = { Text("Názov aktivity") }
        )

        Text(
            text = "Trvanie: ${timer.duration} sekúnd"
        )

        Row {
            Button(
                onClick = { timer.start() },
                enabled = !timer.isRunning
            ) {
                Text("Štart")
            }

            Button(
                onClick = { timer.stop() },
                enabled = timer.isRunning
            ) {
                Text("Stop")
            }

            Button(
                onClick = { timer.reset() }
            ) {
                Text("Reštart")
            }
        }
    }
}