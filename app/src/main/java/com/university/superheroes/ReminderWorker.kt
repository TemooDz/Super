package com.university.superheroes

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat
            .Builder(context, "Channel_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Open app for fun")
            .setContentText("PLease open app and search super heroes")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }

        return Result.Success()
    }
}
