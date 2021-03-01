package com.example.homeworkAA

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.homeworkAA.di.Injection
import com.example.homeworkAA.workers.RefreshWorker
import java.util.concurrent.TimeUnit

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Injection.setup(this)
        createNotificationChannel()
        runWorkManager()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MoviesConstants.CHANNEL_ID,
                this.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = this@MoviesApp.getString(R.string.notification_channel_description)
                setSound(null, null)
                enableVibration(false)
            }
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun runWorkManager() {
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val constrainedRequest = PeriodicWorkRequest.Builder(RefreshWorker::class.java, 8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag(MoviesConstants.WORK_TAG)
            .setInitialDelay(8L, TimeUnit.HOURS)
            .build()

        workManager.enqueue(constrainedRequest)
    }
}