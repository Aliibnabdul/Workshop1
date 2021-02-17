package com.example.homeworkAA

import android.app.Application
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
        runWorkManager()
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