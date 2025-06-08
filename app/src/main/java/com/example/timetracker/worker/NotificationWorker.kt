package com.example.timetracker.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class NotificationWorker (
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            val activityName = inputData.getString("activityName") ?: "Activity"
            val elapsedMinutes = inputData.getLong("elapsedMinutes", 0L)

            val channelId = "timetracker_channel"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "TimeTracker Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val manager = applicationContext.getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
            }

            val builder = NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Aktuálna aktivita: $activityName")
                .setContentText("Čas: $elapsedMinutes minút")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(applicationContext)) {
                notify(1001, builder.build())
            }
        } catch (e: SecurityException) {
            return Result.failure()
        }
        return Result.success()
    }
}
fun startNotification(context: Context, activityName: String) {
    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
        15, TimeUnit.MINUTES
    )
        .setInputData(workDataOf("activityName" to activityName))
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "timer_notification",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )
}
fun cancelNotification(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork("timer_notification")
}