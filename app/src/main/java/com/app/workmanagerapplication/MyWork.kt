package com.app.workmanagerapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class MyWork(context: Context,workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        // Perform your background task here
        // Return Result.SUCCESS if the task is completed successfully

        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setSmallIcon(
                androidx.loader.R.drawable.notification_bg)
            .setContentTitle("Task completed")
            .setContentText("The background task has completed successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return Result.success()
        }
        NotificationManagerCompat.from(applicationContext).notify(1, notification)

        return Result.success()
    }
}