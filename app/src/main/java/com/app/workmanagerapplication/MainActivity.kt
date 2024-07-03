package com.app.workmanagerapplication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        //Create a OneTimeWorkRequest to run one Time
        val workRequest = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .addTag("my_unique_tag") // Add a unique tag
            .build()

        // Create a PeriodicWorkRequest to run every 15 minutes
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            MyWork::class.java,
            15,
            TimeUnit.MINUTES)
            .addTag("my_unique_tag") // Add a unique tag
            .build()

        // Schedule the WorkRequest with OneTimeWorkRequest
        WorkManager.getInstance(this).enqueue(workRequest)

        // Schedule the WorkRequest with PeriodicWorkRequest
        WorkManager.getInstance(this).enqueue(periodicWorkRequest)

        // Cancel the periodic work request
        WorkManager.getInstance(this).cancelUniqueWork("my_unique_tag")

    }
}