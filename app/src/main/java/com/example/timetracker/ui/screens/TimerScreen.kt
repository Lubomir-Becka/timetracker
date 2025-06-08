package com.example.timetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp
import com.example.timetracker.utils.formatSecToHMS


import com.example.timetracker.viewmodel.TimerVM

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimerScreen(timer: TimerVM) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        OutlinedTextField(
            value = timer.name,
            onValueChange = { timer.updateName(it) },
            placeholder = { Text("Názov aktivity") },
            isError = timer.nameError != null,
            supportingText = {
                timer.nameError?.let { errorMsg ->
                    Text(
                        text = errorMsg,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )


        Spacer(modifier = Modifier.height(32.dp))

        Card(
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(16.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .shadow(8.dp, RoundedCornerShape(32.dp))
        ) {
            Text(
                text = formatSecToHMS(timer.duration),
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 48.dp, vertical = 24.dp)
                    .align(Alignment.CenterHorizontally).animateContentSize()
            )
        }

        Spacer(modifier = Modifier.height(108.dp))

        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {
                    if (timer.isRunning) timer.stop() else timer.start()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (timer.isRunning)
                        Color(0xFF4CAF50)
                    else
                        Color(0xFF7C3AED)
                ),
                modifier = Modifier
                    .height(56.dp)
                    .width(140.dp)
            ) {
                Icon(
                    imageVector = if (timer.isRunning) Icons.Filled.Done else Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.width(24.dp))

            Button(
                onClick = { timer.reset() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1)
                ),
                modifier = Modifier
                    .height(56.dp)
                    .width(140.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Button( onClick = {timer.insertSampleData()}) {
            Text(text = "Sample data")
        }
    }
}

